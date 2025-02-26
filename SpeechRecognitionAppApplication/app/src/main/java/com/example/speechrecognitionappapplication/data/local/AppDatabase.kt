package com.example.speechrecognitionappapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.voiceflow.data.model.TranscriptEntity

@Database(entities = [TranscriptEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transcriptDao(): TranscriptDao
}