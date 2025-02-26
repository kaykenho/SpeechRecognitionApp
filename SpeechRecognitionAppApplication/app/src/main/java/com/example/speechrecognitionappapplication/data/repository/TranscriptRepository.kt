package com.example.speechrecognitionappapplication.data.repository

import com.voiceflow.data.model.TranscriptEntity
import com.voiceflow.data.local.TranscriptDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranscriptRepository @Inject constructor(
    private val transcriptDao: TranscriptDao
) {
    fun getAllTranscripts(): Flow<List<TranscriptEntity>> {
        return transcriptDao.getAllTranscripts()
    }

    fun getTranscriptById(id: Long): Flow<TranscriptEntity?> {
        return transcriptDao.getTranscriptById(id)
    }

    suspend fun insertTranscript(transcript: TranscriptEntity): Long {
        return transcriptDao.insertTranscript(transcript)
    }

    suspend fun updateTranscript(transcript: TranscriptEntity) {
        transcriptDao.updateTranscript(transcript)
    }

    suspend fun deleteTranscript(transcript: TranscriptEntity) {
        transcriptDao.deleteTranscript(transcript)
    }

    suspend fun deleteAllTranscripts() {
        transcriptDao.deleteAllTranscripts()
    }
}