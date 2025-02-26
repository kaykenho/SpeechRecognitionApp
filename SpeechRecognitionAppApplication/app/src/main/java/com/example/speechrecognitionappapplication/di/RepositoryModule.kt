package com.example.speechrecognitionappapplication.di

import android.content.Context
import com.voiceflow.data.local.TranscriptDao
import com.voiceflow.data.repository.SettingsRepository
import com.voiceflow.data.repository.TranscriptRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTranscriptRepository(transcriptDao: TranscriptDao): TranscriptRepository {
        return TranscriptRepository(transcriptDao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }
}