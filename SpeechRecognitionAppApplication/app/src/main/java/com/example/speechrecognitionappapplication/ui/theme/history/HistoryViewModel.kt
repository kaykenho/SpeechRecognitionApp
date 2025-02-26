package com.example.speechrecognitionappapplication.ui.theme.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.voiceflow.data.model.TranscriptEntity
import com.voiceflow.data.repository.TranscriptRepository

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TranscriptRepository
) : ViewModel() {

    val transcripts: Flow<List<TranscriptEntity>> = repository.getAllTranscripts()

    fun deleteTranscript(transcript: TranscriptEntity) {
        viewModelScope.launch {
            repository.deleteTranscript(transcript)
        }
    }

    fun clearAllTranscripts() {
        viewModelScope.launch {
            repository.deleteAllTranscripts()
        }
    }
}