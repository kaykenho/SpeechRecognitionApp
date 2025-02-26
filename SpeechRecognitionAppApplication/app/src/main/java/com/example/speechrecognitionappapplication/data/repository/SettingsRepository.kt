package com.example.speechrecognitionappapplication.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.voiceflow.ui.settings.AppSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val HIGH_ACCURACY_MODE = booleanPreferencesKey("high_accuracy_mode")
        val RECOGNITION_SENSITIVITY = floatPreferencesKey("recognition_sensitivity")
        val USE_GPU_ACCELERATION = booleanPreferencesKey("use_gpu_acceleration")
        val NOISE_SUPPRESSION = booleanPreferencesKey("noise_suppression")
        val MIC_SENSITIVITY = floatPreferencesKey("mic_sensitivity")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val KEEP_SCREEN_ON = booleanPreferencesKey("keep_screen_on")
    }

    suspend fun getSettings(): AppSettings? {
        return context.dataStore.data.map { preferences ->
            AppSettings(
                highAccuracyMode = preferences[PreferencesKeys.HIGH_ACCURACY_MODE] ?: true,
                recognitionSensitivity = preferences[PreferencesKeys.RECOGNITION_SENSITIVITY] ?: 0.7f,
                useGpuAcceleration = preferences[PreferencesKeys.USE_GPU_ACCELERATION] ?: true,
                noiseSuppressionEnabled = preferences[PreferencesKeys.NOISE_SUPPRESSION] ?: true,
                microphoneSensitivity = preferences[PreferencesKeys.MIC_SENSITIVITY] ?: 0.8f,
                darkThemeEnabled = preferences[PreferencesKeys.DARK_THEME] ?: false,
                keepScreenOn = preferences[PreferencesKeys.KEEP_SCREEN_ON] ?: true
            )
        }.first()
    }

    suspend fun saveSettings(settings: AppSettings) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.HIGH_ACCURACY_MODE] = settings.highAccuracyMode
            preferences[PreferencesKeys.RECOGNITION_SENSITIVITY] = settings.recognitionSensitivity
            preferences[PreferencesKeys.USE_GPU_ACCELERATION] = settings.useGpuAcceleration
            preferences[PreferencesKeys.NOISE_SUPPRESSION] = settings.noiseSuppressionEnabled
            preferences[PreferencesKeys.MIC_SENSITIVITY] = settings.microphoneSensitivity
            preferences[PreferencesKeys.DARK_THEME] = settings.darkThemeEnabled
            preferences[PreferencesKeys.KEEP_SCREEN_ON] = settings.keepScreenOn
        }
    }
}