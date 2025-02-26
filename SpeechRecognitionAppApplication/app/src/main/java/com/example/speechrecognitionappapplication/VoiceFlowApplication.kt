package com.example.speechrecognitionappapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VoiceFlowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide dependencies here
    }
}