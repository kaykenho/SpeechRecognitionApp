package com.example.speechrecognitionappapplication.ml

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ShortBuffer
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.withLock
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.max

@Singleton
class SpeechRecognizer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tfliteManager: TFLiteManager
) {
    companion object {
        private const val TAG = "SpeechRecognizer"
        private const val SAMPLE_RATE = 16000 // Hz
        private const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
        private const val BUFFER_SIZE_FACTOR = 2
        private const val FRAME_SIZE = 512 // Process audio in frames
        private const val AMPLITUDE_HISTORY_SIZE = 50 // Number of amplitude values to keep
    }

    private var audioRecord: AudioRecord? = null
    private var isRecordingActive = false
    private val lock = ReentrantLock()
    private val audioBuffer: ShortArray by lazy {
        ShortArray(FRAME_SIZE)
    }

    // For amplitude calculation
    private val amplitudes = mutableListOf<Float>()

    /**
     * Start recording audio for recognition
     * @param callback A callback function that receives the audio data and current amplitude values
     */
    suspend fun startRecording(
        callback: suspend (ShortArray, List<Float>) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            lock.withLock {
                if (isRecordingActive) return@withContext

                val bufferSize = max(
                    AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT),
                    FRAME_SIZE * BUFFER_SIZE_FACTOR
                )

                audioRecord = AudioRecord(
                    MediaRecorder.AudioSource.VOICE_RECOGNITION,
                    SAMPLE_RATE,
                    CHANNEL_CONFIG,
                    AUDIO_FORMAT,
                    bufferSize
                )

                if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
                    Log.e(TAG, "AudioRecord initialization failed")
                    return@withContext
                }

                audioRecord?.startRecording()
                isRecordingActive = true
            }

            // Clear amplitude history
            amplitudes.clear()

            // Processing loop
            while (isRecordingActive) {
                val audioData = ShortArray(FRAME_SIZE)
                val readSize = audioRecord?.read(audioData, 0, FRAME_SIZE) ?: 0

                if (readSize > 0) {
                    // Calculate RMS amplitude and add to history
                    val amplitude = calculateRMSAmplitude(audioData)
                    updateAmplitudes(amplitude)

                    // Send data to callback
                    callback(audioData, amplitudes.toList())
                }

                delay(10) // Brief delay to prevent CPU overuse
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in recording: ${e.message}")
            stopRecording()
        }
    }

    /**
     * Stop recording audio
     */
    fun stopRecording() {
        lock.withLock {
            isRecordingActive = false
            try {
                audioRecord?.stop()
                audioRecord?.release()
                audioRecord = null
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping recording: ${e.message}")
            }
        }
    }

    /**
     * Process the audio features through the TFLite model
     */
    fun recognize(audioFeatures: FloatArray): String {
        return try {
            tfliteManager.runInference(audioFeatures)
        } catch (e: Exception) {
            Log.e(TAG, "Error in recognition: ${e.message}")
            ""
        }
    }

    /**
     * Calculate RMS amplitude from audio buffer
     */
    private fun calculateRMSAmplitude(buffer: ShortArray): Float {
        var sumSquared = 0.0
        for (sample in buffer) {
            // Convert to -1.0 to 1.0 range
            val normalizedSample = sample / 32768.0
            sumSquared += normalizedSample * normalizedSample
        }

        val rms = Math.sqrt(sumSquared / buffer.size)

        // Apply logarithmic scaling for better visualization
        return (1.0 + 0.2 * ln(1.0 + 10 * rms)).toFloat()
    }

    /**
     * Update amplitude history
     */
    private fun updateAmplitudes(newAmplitude: Float) {
        amplitudes.add(newAmplitude)
        if (amplitudes.size > AMPLITUDE_HISTORY_SIZE) {
            amplitudes.removeAt(0)
        }
    }
}