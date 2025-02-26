package com.example.speechrecognitionappapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.hilt.navigation.compose.hiltViewModel
import com.voiceflow.ui.theme.VoiceFlowTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import com.voiceflow.ui.recording.RecordingViewModel
import com.voiceflow.ui.home.HomeScreen
import com.voiceflow.ui.history.HistoryScreen
import com.voiceflow.ui.settings.SettingsScreen
import com.voiceflow.ui.components.AudioVisualizer
import com.lucide.react.Mic
import com.lucide.react.History
import com.lucide.react.Settings
import com.lucide.react.X
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoiceFlowTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: RecordingViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isRecording by viewModel.isRecording.collectAsState()
    val transcribedText by viewModel.transcribedText.collectAsState()
    val audioAmplitudes by viewModel.audioAmplitudes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VoiceFlow") },
                actions = {
                    IconButton(onClick = { navController.navigate("history") }) {
                        Icon(History(), contentDescription = "History")
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Settings(), contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            RecordButton(
                isRecording = isRecording,
                onRecordToggle = { viewModel.toggleRecording() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    isRecording = isRecording,
                    transcribedText = transcribedText,
                    audioAmplitudes = audioAmplitudes,
                    onClearText = { viewModel.clearTranscription() }
                )
            }
            composable("history") {
                HistoryScreen(navController = navController)
            }
            composable("settings") {
                SettingsScreen(navController = navController)
            }
        }
    }
}

@Composable
fun RecordButton(
    isRecording: Boolean,
    onRecordToggle: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isRecording) 1.2f else 1f,
        label = "scale"
    )

    FloatingActionButton(
        onClick = onRecordToggle,
        containerColor = if (isRecording)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.primary,
        modifier = Modifier.scale(scale)
    ) {
        Icon(
            if (isRecording) X() else Mic(),
            contentDescription = if (isRecording) "Stop recording" else "Start recording",
            tint = Color.White
        )
    }
}