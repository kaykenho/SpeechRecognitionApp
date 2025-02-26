package com.example.speechrecognitionappapplication.di

import android.content.Context
import com.voiceflow.ml.AudioProcessor
import com.voiceflow.ml.SpeechRecognizer
import com.voiceflow.ml.TFLiteManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MLModule {

    @Provides
    @Singleton
    fun provideTFLiteManager(@ApplicationContext context: Context): TFLiteManager {
        return TFLiteManager(context)
    }

    @Provides
    @Singleton
    fun provideAudioProcessor(): AudioProcessor {
        return AudioProcessor()
    }

    @Provides
    @Singleton
    fun provideSpeechRecognizer(
        @ApplicationContext context: Context,
        tfliteManager: TFLiteManager
    ): SpeechRecognizer {
        return SpeechRecognizer(context, tfliteManager)
    }
}