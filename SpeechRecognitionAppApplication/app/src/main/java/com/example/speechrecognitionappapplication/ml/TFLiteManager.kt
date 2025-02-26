package com.example.speechrecognitionappapplication.ml

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TFLiteManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "TFLiteManager"
        private const val MODEL_FILE = "speech_recognition_model_h5_quantized.tflite"

        // These values should match your model's expected input/output
        private const val INPUT_FEATURE_SIZE = 13 // MFCC feature size
        private const val MAX_SEQ_LENGTH = 100 // Maximum sequence length
        private const val OUTPUT_SIZE = 128 // Adjust based on your model's output
    }

    private var interpreter: Interpreter? = null

    init {
        try {
            setupInterpreter()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to load TF Lite model: ${e.message}")
        }
    }

    /**
     * Set up the TFLite interpreter with appropriate options
     */
    private fun setupInterpreter() {
        val options = Interpreter.Options().apply {
            // Add the Flex delegate to support TF ops
            addDelegate(org.tensorflow.lite.support.common.ops.FlexDelegate())

            // Try to use GPU if available
            val compatList = CompatibilityList()
            if (compatList.isDelegateSupportedOnThisDevice) {
                val delegateOptions = compatList.bestOptionsForThisDevice
                addDelegate(GpuDelegate(delegateOptions))
            } else {
                // If GPU not available, optimize for CPU
                setNumThreads(4)
            }

            // Enable memory optimization
            setUseNNAPI(true)
        }

        interpreter = Interpreter(loadModelFile(context, MODEL_FILE), options)
        Log.d(TAG, "TFLite interpreter initialized successfully")
    }

    /**
     * Load the TFLite model file from assets
     */
    private fun loadModelFile(context: Context, modelPath: String): ByteBuffer {
        val assetManager = context.assets
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    /**
     * Run inference on audio features
     */
    fun runInference(audioFeatures: FloatArray): String {
        if (interpreter == null) {
            Log.e(TAG, "Interpreter is not initialized")
            return ""
        }

        try {
            // Prepare input data - adjust shape and size according to your model
            val inputBuffer = ByteBuffer.allocateDirect(MAX_SEQ_LENGTH * INPUT_FEATURE_SIZE * 4)
                .order(ByteOrder.nativeOrder())

            // Fill buffer with feature data (padded to max length if needed)
            for (i in 0 until MAX_SEQ_LENGTH) {
                for (j in 0 until INPUT_FEATURE_SIZE) {
                    val index = i * INPUT_FEATURE_SIZE + j
                    val value = if (index < audioFeatures.size) audioFeatures[index] else 0f
                    inputBuffer.putFloat(value)
                }
            }
            inputBuffer.rewind()

            // Prepare output buffer
            val outputBuffer = ByteBuffer.allocateDirect(OUTPUT_SIZE * 4)
                .order(ByteOrder.nativeOrder())

            // Run inference
            interpreter?.run(inputBuffer, outputBuffer)

            // Process the output to text
            return processOutputBuffer(outputBuffer)

        } catch (e: Exception) {
            Log.e(TAG, "Error during inference: ${e.message}")
            return ""
        }
    }

    /**
     * Process the raw output from the model into text
     */
    private fun processOutputBuffer(outputBuffer: ByteBuffer): String {
        outputBuffer.rewind()

        // This processing depends on your model's output format
        // For demonstration, we'll assume the output is a sequence of probabilities
        // that we need to decode into text

        // Example for decoding - adjust based on your specific model's output
        // This is a placeholder implementation that would need to be customized

        val outputFeatures = FloatArray(OUTPUT_SIZE)
        for (i in 0 until OUTPUT_SIZE) {
            outputFeatures[i] = outputBuffer.getFloat()
        }

        // Convert the raw features to text using a character mapping
        // This is where you would implement your specific decoding logic
        // based on how your model was trained

        // For demonstration purposes, we're returning a simple message
        return "Recognized speech segment"
    }

    /**
     * Clean up resources when no longer needed
     */
    fun close() {
        interpreter?.close()
        interpreter = null
    }
}