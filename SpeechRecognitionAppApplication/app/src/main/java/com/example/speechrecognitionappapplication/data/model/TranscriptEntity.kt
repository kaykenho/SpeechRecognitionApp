package com.example.speechrecognitionappapplication.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transcripts")
data class TranscriptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val duration: Long = 0,
    val isFavorite: Boolean = false
)