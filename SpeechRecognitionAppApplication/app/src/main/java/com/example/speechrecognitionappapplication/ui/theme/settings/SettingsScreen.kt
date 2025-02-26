package com.example.speechrecognitionappapplication.ui.theme.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lucide.react.ArrowLeft
import com.lucide.react.HelpCircle
import com.lucide.react.Info

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState(initial = AppSettings())
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Top app bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(ArrowLeft(), contentDescription = "Back")
            }

            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Model settings
        SettingsSection(title = "Speech Recognition") {
            SwitchSetting(
                title = "High accuracy mode",
                description = "Uses more CPU but improves recognition quality",
                checked = settings.highAccuracyMode,
                onCheckedChange = { viewModel.updateHighAccuracyMode(it) }
            )

            SliderSetting(
                title = "Recognition sensitivity",
                value = settings.recognitionSensitivity,
                onValueChange = { viewModel.updateRecognitionSensitivity(it) }
            )

            SwitchSetting(
                title = "Use GPU acceleration",
                description = "Speeds up processing on supported devices",
                checked = settings.useGpuAcceleration,
                onCheckedChange = { viewModel.updateGpuAcceleration(it) }
            )
        }

        // Audio settings
        SettingsSection(title = "Audio") {
            SwitchSetting(
                title = "Noise suppression",
                description = "Reduces background noise in recordings",
                checked = settings.noiseSuppressionEnabled,
                onCheckedChange = { viewModel.updateNoiseSuppression(it) }
            )

            SliderSetting(
                title = "Microphone sensitivity",
                value = settings.microphoneSensitivity,
                onValueChange = { viewModel.updateMicrophoneSensitivity(it) }
            )
        }

        // App settings
        SettingsSection(title = "Appearance") {
            SwitchSetting(
                title = "Dark theme",
                description = "Use dark colors for the interface",
                checked = settings.darkThemeEnabled,
                onCheckedChange = { viewModel.updateDarkTheme(it) }
            )

            SwitchSetting(
                title = "Keep screen on",
                description = "Prevents screen from turning off during recording",
                checked = settings.keepScreenOn,
                onCheckedChange = { viewModel.updateKeepScreenOn(it) }
            )
        }

        // About section
        SettingsSection(title = "About") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Info(),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = "VoiceFlow",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Version 1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        content()

        Divider(
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Composable
fun SwitchSetting(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SliderSetting(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${(value * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f
        )
    }
}