package com.example.speechrecognitionappapplication.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.voiceflow.ui.components.AudioVisualizer

@Composable
fun HomeScreen(
    isRecording: Boolean,
    transcribedText: String,
    audioAmplitudes: List<Float>,
    onClearText: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Audio visualizer - only shown when recording
        AnimatedVisibility(
            visible = isRecording,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (audioAmplitudes.isNotEmpty()) {
                        AudioVisualizer(
                            amplitudes = audioAmplitudes,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Text(
                        text = "Listening...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.alpha(0.7f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Transcription area
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Transcription",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    if (transcribedText.isEmpty()) {
                        Text(
                            text = "Start recording to see transcription here",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(24.dp)
                        )
                    } else {
                        Text(
                            text = transcribedText,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onClearText,
                enabled = transcribedText.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Clear")
            }

            Button(
                onClick = { /* Save functionality */ },
                enabled = transcribedText.isNotEmpty()
            ) {
                Text("Save")
            }
        }
    }
}