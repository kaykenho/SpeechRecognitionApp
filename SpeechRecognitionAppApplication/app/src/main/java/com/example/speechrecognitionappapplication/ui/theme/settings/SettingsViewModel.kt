package com.example.speechrecognitionappapplication.ui.theme.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.voiceflow.data.repository.SettingsRepository

data class AppSettings(
    val highAccuracyMode: Boolean = true,
    val recognitionSensitivity: Float = 0.7f,
    val useGpuAcceleration: Boolean = true,
    val noiseSuppressionEnabled: Boolean = true,
    val microphoneSensitivity: Float = 0.8f,
    val darkThemeEnabled: Boolean = false,
    val keepScreenOn: Boolean = true
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val savedSettings = repository.getSettings()
            _settings.value = savedSettings ?: AppSettings()
        }
    }

    fun updateHighAccuracyMode(enabled: Boolean) {
        viewModelScope.launch {
            val updatedSettings = _settings.value.copy(highAccuracyMode = enabled)
            _settings.value = updatedSettings
            repository.saveSettings(updatedSettings)
        }
    }

    fun updateRecognitionSensitivity(value: Float) {
        viewModelScope.launch {
            val updatedSettings = _settings.value.copy(recognitionSensitivity = value)
            _settings.value = updatedSettings
            repository.saveSettings(updatedSettings)
        }
    }

    fun updateGpuAcceleration(enabled: Boolean) {
        viewModelScope.launch {
            val updatedSettings = _settings.value.copy(useGpuAcceleration = enabled)
            _settings.value = updatedSettings
            repository.saveSettings(updatedSettings)
        }
    }

    fun updateNoiseSuppression(enabled: Boolean) {
        viewModelScope.launch {
            val updatedSettings = _settings.value.copy(noiseSuppressionEnabled = enabled)
            _settings.value = updatedSettings
            repository.saveSettings(updatedSettings)
        }
    }

    fun updateMicrophoneSensitivity(value: Float) {
        viewModelScope.launch {
            val updatedSettings = _settings.value.copy(microphoneSensitivity = value)
            _settings.value = updatedSettings
            repository.saveSettings(updatedSettings)
        }
    }

    fun updateDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            val updatedSettings = _settings.value.copy(darkThemeEnabled = enabled)
            _settings.value = updatedSettings
            repository.saveSettings(updatedSettings)
        }
    }

    fun updateKeepScreenOn(enabled: Boolean) {
        viewModelScope.launch {
            val updatedSettings = _settings.value.copy(keepScreenOn = enabled)
            _settings.value = updatedSettings
            repository.saveSettings(updatedSettings)
        }
    }
}