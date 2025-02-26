package com.example.speechrecognitionappapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.voiceflow.data.model.TranscriptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TranscriptDao {
    @Query("SELECT * FROM transcripts ORDER BY timestamp DESC")
    fun getAllTranscripts(): Flow<List<TranscriptEntity>>

    @Query("SELECT * FROM transcripts WHERE id = :id")
    fun getTranscriptById(id: Long): Flow<TranscriptEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranscript(transcript: TranscriptEntity): Long

    @Update
    suspend fun updateTranscript(transcript: TranscriptEntity)

    @Delete
    suspend fun deleteTranscript(transcript: TranscriptEntity)

    @Query("DELETE FROM transcripts")
    suspend fun deleteAllTranscripts()
}