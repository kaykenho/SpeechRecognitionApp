package com.example.speechrecognitionappapplication.di

import android.content.Context
import androidx.room.Room
import com.voiceflow.data.local.AppDatabase
import com.voiceflow.data.local.TranscriptDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "voiceflow_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTranscriptDao(database: AppDatabase): TranscriptDao {
        return database.transcriptDao()
    }
}