package com.example.speechrecognitionappapplication.ui.theme.recording

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.voiceflow.ml.SpeechRecognizer
import com.voiceflow.ml.AudioProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.lang.StringBuilder

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val speechRecognizer: SpeechRecognizer,
    private val audioProcessor: AudioProcessor
) : ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _transcribedText = MutableStateFlow("")
    val transcribedText: StateFlow<String> = _transcribedText.asStateFlow()

    private val _audioAmplitudes = MutableStateFlow<List<Float>>(emptyList())
    val audioAmplitudes: StateFlow<List<Float>> = _audioAmplitudes.asStateFlow()

    fun toggleRecording() {
        val newRecordingState = !_isRecording.value
        _isRecording.value = newRecordingState

        if (newRecordingState) {
            startRecording()
        } else {
            stopRecording()
        }
    }

    private fun startRecording() {
        viewModelScope.launch(Dispatchers.IO) {
            speechRecognizer.startRecording { audioData, amplitudes ->
                // Update audio visualization
                _audioAmplitudes.value = amplitudes

                // Process audio for recognition
                val processedFeatures = audioProcessor.extractFeatures(audioData)
                val recognizedText = speechRecognizer.recognize(processedFeatures)

                if (recognizedText.isNotEmpty()) {
                    val currentText = _transcribedText.value
                    _transcribedText.value = if (currentText.isEmpty()) {
                        recognizedText
                    } else {
                        "$currentText $recognizedText"
                    }
                }
            }
        }

        // Simulate amplitude changes for preview
        if (isPreview()) {
            viewModelScope.launch {
                while (isActive && _isRecording.value) {
                    val simulatedAmplitudes = List(30) {
                        (Math.random() * 0.8f + 0.2f).toFloat()
                    }
                    _audioAmplitudes.value = simulatedAmplitudes
                    delay(100)
                }
            }
        }
    }

    private fun stopRecording() {
        speechRecognizer.stopRecording()
        _audioAmplitudes.value = emptyList()
    }

    fun clearTranscription() {
        _transcribedText.value = ""
    }

    private fun isPreview(): Boolean {
        return try {
            Class.forName("androidx.compose.ui.tooling.preview.Preview")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}