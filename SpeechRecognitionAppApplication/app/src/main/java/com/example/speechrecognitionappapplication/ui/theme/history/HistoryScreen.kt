package com.example.speechrecognitionappapplication.ui.theme.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lucide.react.ArrowLeft
import com.lucide.react.Delete
import com.lucide.react.Edit
import com.lucide.react.Share
import com.voiceflow.data.model.TranscriptEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val transcripts by viewModel.transcripts.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        // Custom top bar
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
                text = "Transcript History",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (transcripts.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No transcripts yet. Start recording to create some!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            // Transcript list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(transcripts) { transcript ->
                    TranscriptItem(
                        transcript = transcript,
                        onItemClick = {
                            // Navigate to transcript detail screen
                            // navController.navigate("transcript_detail/${transcript.id}")
                        },
                        onDeleteClick = {
                            viewModel.deleteTranscript(transcript)
                        }
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}

@Composable
fun TranscriptItem(
    transcript: TranscriptEntity,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = transcript.title.ifEmpty { "Untitled Recording" },
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = formatDate(transcript.timestamp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        Delete(),
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                IconButton(onClick = { /* Share functionality */ }) {
                    Icon(
                        Share(),
                        contentDescription = "Share"
                    )
                }
            }

            Text(
                text = transcript.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
    return format.format(date)
}