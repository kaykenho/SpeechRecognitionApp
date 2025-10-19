# Speech Recognition Project

![svgviewer-output](https://github.com/user-attachments/assets/66de968e-de82-41b0-9394-fa53250ab9ab)


![Upl<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 900 700">
  <!-- Background -->
  <rect width="900" height="700" fill="#f8f9fa" rx="10" ry="10"/>
  
  <!-- Title -->
  <text x="450" y="40" font-family="Arial" font-size="24" text-anchor="middle" font-weight="bold">Speech Recognition Project Architecture</text>

  SpeechRecognitionApp/
│
├── README.md
├── .gitignore
│
├── ML/                                    # Machine Learning Component (Python)
│   ├── preprocessing/
│   │   └── data_loader.py               # Audio data loading and preprocessing
│   │
│   ├── models/
│   │   └── model.py                     # Neural network architecture definition
│   │
│   ├── quantization/
│   │   └── quantize_model.py            # Model optimization and quantization
│   │
│   ├── train.py                         # Model training script
│   ├── inference.py                     # Model inference/prediction script
│   │
│   └── speech_recognition_model_h5_quantized.tflite  # Optimized TFLite model
│
├── android/                              # Android Application (Kotlin)
│   ├── app/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/yourpackage/
│   │   │   │   │   │
│   │   │   │   │   ├── ui/             # UI Layer
│   │   │   │   │   │   ├── home/
│   │   │   │   │   │   │   └── HomeScreen.kt
│   │   │   │   │   │   ├── history/
│   │   │   │   │   │   │   └── HistoryScreen.kt
│   │   │   │   │   │   ├── settings/
│   │   │   │   │   │   │   └── SettingsScreen.kt
│   │   │   │   │   │   └── components/
│   │   │   │   │   │       └── AudioVisualizer.kt
│   │   │   │   │   │
│   │   │   │   │   ├── viewmodel/     # Business Logic Layer
│   │   │   │   │   │   ├── RecordingViewModel.kt
│   │   │   │   │   │   ├── HistoryViewModel.kt
│   │   │   │   │   │   └── SettingsViewModel.kt
│   │   │   │   │   │
│   │   │   │   │   ├── data/          # Data Layer
│   │   │   │   │   │   ├── local/
│   │   │   │   │   │   │   ├── database/
│   │   │   │   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   │   │   │   ├── TranscriptDao.kt
│   │   │   │   │   │   │   │   └── TranscriptEntity.kt
│   │   │   │   │   │   │   └── repository/
│   │   │   │   │   │   │       └── TranscriptRepository.kt
│   │   │   │   │   │   │
│   │   │   │   │   │   └── ml/       # ML Integration
│   │   │   │   │   │       ├── AudioProcessor.kt
│   │   │   │   │   │       ├── SpeechRecognizer.kt
│   │   │   │   │   │       └── TFLiteManager.kt
│   │   │   │   │   │
│   │   │   │   │   └── di/           # Dependency Injection
│   │   │   │   │       └── AppModule.kt
│   │   │   │   │
│   │   │   │   ├── assets/
│   │   │   │   │   └── speech_recognition_model_h5_quantized.tflite
│   │   │   │   │
│   │   │   │   ├── res/
│   │   │   │   │   ├── layout/
│   │   │   │   │   ├── values/
│   │   │   │   │   └── drawable/
│   │   │   │   │
│   │   │   │   └── AndroidManifest.xml
│   │   │   │
│   │   │   └── test/
│   │   │
│   │   ├── build.gradle
│   │   └── proguard-rules.pro
│   │
│   ├── gradle/
│   ├── build.gradle
│   ├── settings.gradle
│   └── gradle.properties
│
└── docs/
    ├── architecture.md
    ├── ml_pipeline.md
    └── android_setup.md
  
  <!-- Main Sections -->
  <rect x="50" y="70" width="800" height="300" fill="#e6f3ff" stroke="#0066cc" stroke-width="2" rx="10" ry="10"/>
  <text x="450" y="95" font-family="Arial" font-size="18" text-anchor="middle" font-weight="bold">ML Component (Python)</text>
  
  <rect x="50" y="390" width="800" height="280" fill="#e6ffe6" stroke="#009933" stroke-width="2" rx="10" ry="10"/>
  <text x="450" y="415" font-family="Arial" font-size="18" text-anchor="middle" font-weight="bold">Android Application (Kotlin)</text>
  
  <!-- ML Component Details -->
  <!-- Data Flow Box -->
  <rect x="70" y="120" width="200" height="220" fill="#d1e7ff" stroke="#0066cc" stroke-width="1" rx="5" ry="5"/>
  <text x="170" y="140" font-family="Arial" font-size="16" text-anchor="middle" font-weight="bold">Data Processing</text>
  
  <!-- Model Box -->
  <rect x="290" y="120" width="200" height="220" fill="#d1e7ff" stroke="#0066cc" stroke-width="1" rx="5" ry="5"/>
  <text x="390" y="140" font-family="Arial" font-size="16" text-anchor="middle" font-weight="bold">Model Training</text>
  
  <!-- Optimization Box -->
  <rect x="510" y="120" width="200" height="220" fill="#d1e7ff" stroke="#0066cc" stroke-width="1" rx="5" ry="5"/>
  <text x="610" y="140" font-family="Arial" font-size="16" text-anchor="middle" font-weight="bold">Optimization</text>
  
  <!-- ML Details -->
  <!-- Data Processing Details -->
  <rect x="85" y="155" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="175" font-family="Arial" font-size="12" text-anchor="middle">preprocessing/data_loader.py</text>
  
  <rect x="85" y="195" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="215" font-family="Arial" font-size="12" text-anchor="middle">Audio Feature Extraction</text>
  
  <rect x="85" y="235" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="255" font-family="Arial" font-size="12" text-anchor="middle">Data Augmentation</text>
  
  <rect x="85" y="275" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="295" font-family="Arial" font-size="12" text-anchor="middle">Batching &amp; Preparation</text>
  
  <!-- Model Training Details -->
  <rect x="305" y="155" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="175" font-family="Arial" font-size="12" text-anchor="middle">models/model.py</text>
  
  <rect x="305" y="195" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="215" font-family="Arial" font-size="12" text-anchor="middle">Neural Network Design</text>
  
  <rect x="305" y="235" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="255" font-family="Arial" font-size="12" text-anchor="middle">train.py</text>
  
  <rect x="305" y="275" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="295" font-family="Arial" font-size="12" text-anchor="middle">inference.py</text>
  
  <!-- Optimization Details -->
  <rect x="525" y="155" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="610" y="175" font-family="Arial" font-size="12" text-anchor="middle">quantization/quantize_model.py</text>
  
  <rect x="525" y="195" width="170" height="30" fill="#ffffff" stroke="#0066cc" stroke-width="1" rx="3" ry="3"/>
  <text x="610" y="215" font-family="Arial" font-size="12" text-anchor="middle">Model Compression</text>
  
  <rect x="525" y="235" width="170" height="60" fill="#ffcc99" stroke="#ff6600" stroke-width="1" rx="3" ry="3"/>
  <text x="610" y="255" font-family="Arial" font-size="12" text-anchor="middle" font-weight="bold">TFLite Model</text>
  <text x="610" y="275" font-family="Arial" font-size="10" text-anchor="middle">speech_recognition_model_h5</text>
  <text x="610" y="287" font-family="Arial" font-size="10" text-anchor="middle">_quantized.tflite</text>
  
  <!-- Android Component Details -->
  <!-- UI Box -->
  <rect x="70" y="440" width="200" height="200" fill="#d1ffd1" stroke="#009933" stroke-width="1" rx="5" ry="5"/>
  <text x="170" y="460" font-family="Arial" font-size="16" text-anchor="middle" font-weight="bold">UI Layer</text>
  
  <!-- Business Logic Box -->
  <rect x="290" y="440" width="200" height="200" fill="#d1ffd1" stroke="#009933" stroke-width="1" rx="5" ry="5"/>
  <text x="390" y="460" font-family="Arial" font-size="16" text-anchor="middle" font-weight="bold">Business Logic</text>
  
  <!-- Data Layer Box -->
  <rect x="510" y="440" width="320" height="200" fill="#d1ffd1" stroke="#009933" stroke-width="1" rx="5" ry="5"/>
  <text x="670" y="460" font-family="Arial" font-size="16" text-anchor="middle" font-weight="bold">Data Layer</text>
  
  <!-- Android Details -->
  <!-- UI Details -->
  <rect x="85" y="475" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="495" font-family="Arial" font-size="12" text-anchor="middle">HomeScreen.kt</text>
  
  <rect x="85" y="515" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="535" font-family="Arial" font-size="12" text-anchor="middle">HistoryScreen.kt</text>
  
  <rect x="85" y="555" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="575" font-family="Arial" font-size="12" text-anchor="middle">SettingsScreen.kt</text>
  
  <rect x="85" y="595" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="170" y="615" font-family="Arial" font-size="12" text-anchor="middle">AudioVisualizer.kt</text>
  
  <!-- Business Logic Details -->
  <rect x="305" y="475" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="495" font-family="Arial" font-size="12" text-anchor="middle">RecordingViewModel.kt</text>
  
  <rect x="305" y="515" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="535" font-family="Arial" font-size="12" text-anchor="middle">HistoryViewModel.kt</text>
  
  <rect x="305" y="555" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="575" font-family="Arial" font-size="12" text-anchor="middle">SettingsViewModel.kt</text>
  
  <rect x="305" y="595" width="170" height="30" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="390" y="615" font-family="Arial" font-size="12" text-anchor="middle">Dependency Injection</text>
  
  <!-- Data Layer Details -->
  <rect x="525" y="475" width="140" height="85" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="595" y="492" font-family="Arial" font-size="12" text-anchor="middle" font-weight="bold">Local Storage</text>
  <text x="595" y="510" font-family="Arial" font-size="10" text-anchor="middle">AppDatabase.kt</text>
  <text x="595" y="525" font-family="Arial" font-size="10" text-anchor="middle">TranscriptDao.kt</text>
  <text x="595" y="540" font-family="Arial" font-size="10" text-anchor="middle">TranscriptEntity.kt</text>
  <text x="595" y="555" font-family="Arial" font-size="10" text-anchor="middle">Repositories</text>
  
  <rect x="675" y="475" width="140" height="85" fill="#ffffff" stroke="#009933" stroke-width="1" rx="3" ry="3"/>
  <text x="745" y="492" font-family="Arial" font-size="12" text-anchor="middle" font-weight="bold">ML Integration</text>
  <text x="745" y="510" font-family="Arial" font-size="10" text-anchor="middle">AudioProcessor.kt</text>
  <text x="745" y="525" font-family="Arial" font-size="10" text-anchor="middle">SpeechRecognizer.kt</text>
  <text x="745" y="540" font-family="Arial" font-size="10" text-anchor="middle">TFLiteManager.kt</text>
  
  <rect x="525" y="570" width="290" height="60" fill="#ffcc99" stroke="#ff6600" stroke-width="1" rx="3" ry="3"/>
  <text x="670" y="590" font-family="Arial" font-size="12" text-anchor="middle" font-weight="bold">TFLite Model (assets)</text>
  <text x="670" y="610" font-family="Arial" font-size="10" text-anchor="middle">speech_recognition_model_h5_quantized.tflite</text>
  
  <!-- Connection Arrows -->
  <!-- ML Flow -->
  <line x1="170" y1="340" x2="390" y2="340" stroke="#0066cc" stroke-width="2" marker-end="url(#arrowBlue)"/>
  <line x1="390" y1="340" x2="610" y2="340" stroke="#0066cc" stroke-width="2" marker-end="url(#arrowBlue)"/>
  
  <!-- Model to Android -->
  <line x1="610" y1="340" x2="730" y2="340" stroke="#0066cc" stroke-width="2"/>
  <line x1="730" y1="340" x2="730" y2="390" stroke="#663399" stroke-width="2" stroke-dasharray="5,5" marker-end="url(#arrowPurple)"/>
  <text x="745" y="375" font-family="Arial" font-size="12" fill="#663399">Model Transfer</text>
  
  <!-- Android Flow -->
  <line x1="170" y1="640" x2="390" y2="640" stroke="#009933" stroke-width="2" marker-end="url(#arrowGreen)"/>
  <line x1="390" y1="640" x2="670" y2="640" stroke="#009933" stroke-width="2" marker-end="url(#arrowGreen)"/>
  
  <!-- Arrow Definitions -->
  <defs>
    <marker id="arrowBlue" markerWidth="10" markerHeight="10" refX="9" refY="3" orient="auto" markerUnits="strokeWidth">
      <path d="M0,0 L0,6 L9,3 z" fill="#0066cc"/>
    </marker>
    <marker id="arrowGreen" markerWidth="10" markerHeight="10" refX="9" refY="3" orient="auto" markerUnits="strokeWidth">
      <path d="M0,0 L0,6 L9,3 z" fill="#009933"/>
    </marker>
    <marker id="arrowPurple" markerWidth="10" markerHeight="10" refX="9" refY="3" orient="auto" markerUnits="strokeWidth">
      <path d="M0,0 L0,6 L9,3 z" fill="#663399"/>
    </marker>
  </defs>
</svg>oading svgviewer-output.svg…]()


## Overview
This project implements a speech recognition system with a machine learning backend and an Android frontend application. The system is designed to process audio input, extract features, and convert speech to text using a trained neural network model that has been optimized for mobile deployment.

## Architecture
The project is split into two main components:
1. **ML Component (Python)** - Responsible for data processing, model training, and optimization
2. **Android Application (Kotlin)** - Provides the user interface and integrates the trained model

### ML Component (Python)

#### Data Processing
- **preprocessing/data_loader.py**: Handles loading and initial processing of audio data
- **Audio Feature Extraction**: Converts raw audio into features (likely MFCC, spectrograms, etc.)
- **Data Augmentation**: Applies techniques to increase training data variety (pitch shifting, noise addition, etc.)
- **Batching & Preparation**: Prepares data in batches for efficient model training

#### Model Training
- **models/model.py**: Defines the neural network architecture
- **Neural Network Design**: Specifies layers, parameters, and overall structure
- **train.py**: Handles the training process (epochs, optimization, etc.)
- **inference.py**: Provides functionality for making predictions with the trained model

#### Optimization
- **quantization/quantize_model.py**: Handles the post-training quantization process
- **Model Compression**: Reduces model size while maintaining accuracy
- **TFLite Model**: Final optimized model (`speech_recognition_model_h5_quantized.tflite`) ready for mobile deployment

### Android Application (Kotlin)

#### UI Layer
- **HomeScreen.kt**: Main interface for audio recording and transcription
- **HistoryScreen.kt**: Displays past transcriptions
- **SettingsScreen.kt**: User preferences and configuration
- **AudioVisualizer.kt**: Visual representation of audio input

#### Business Logic
- **RecordingViewModel.kt**: Manages audio recording state and processes
- **HistoryViewModel.kt**: Handles data operations for transcript history
- **SettingsViewModel.kt**: Manages user settings and preferences
- **Dependency Injection**: Handles component dependencies and lifecycle

#### Data Layer
- **Local Storage**:
  - **AppDatabase.kt**: Room database configuration
  - **TranscriptDao.kt**: Data access object for transcript operations
  - **TranscriptEntity.kt**: Database entity for transcript data
  - **Repositories**: Mediates between ViewModels and data sources

- **ML Integration**:
  - **AudioProcessor.kt**: Processes raw audio for model input
  - **SpeechRecognizer.kt**: Interfaces with the TFLite model
  - **TFLiteManager.kt**: Handles model loading and execution
  - **TFLite Model**: The optimized model file stored in app assets

## Data Flow

### ML Component Flow
1. Audio data is processed through the data processing pipeline
2. The processed data is used to train the neural network model
3. The trained model is optimized and converted to TFLite format

### Model Transfer
The optimized TFLite model is transferred to the Android application's assets folder.

### Android Component Flow
1. User interactions are captured through the UI layer
2. The business logic layer processes these interactions
3. The data layer handles storage and ML model inference

## Setup and Installation

### ML Component
1. Set up a Python environment with TensorFlow and required dependencies
2. Prepare your audio dataset in the format expected by data_loader.py
3. Run the training pipeline:
   ```
   python train.py
   ```
4. Optimize the model for mobile:
   ```
   python quantization/quantize_model.py
   ```

### Android Application
1. Clone the repository and open in Android Studio
2. Ensure the TFLite model is placed in the assets folder
3. Build and run the application on your Android device

## Usage

### Recording Speech
1. Open the app and navigate to the HomeScreen
2. Press the record button to start capturing audio
3. Speak clearly into the device microphone
4. The app will process the audio and display the transcription

### Viewing History
1. Navigate to the HistoryScreen to view past transcriptions
2. Tap on any entry to see full details

### Settings
1. Navigate to the SettingsScreen to configure app preferences
2. Adjust audio settings or transcription options as needed

## Technical Requirements
- Python 3.7+ with TensorFlow 2.x for the ML component
- Android Studio with Kotlin support for the Android application
- Android device running API level 21+ (Android 5.0 or higher)

## Dependencies
- TensorFlow and TensorFlow Lite
- Room Database for Android
- AndroidX libraries
- Kotlin Coroutines for asynchronous operations

## Future Improvements
- Enhance model accuracy with more training data
- Add support for multiple languages
- Implement real-time transcription feedback
- Cloud synchronization for transcriptions
- Offline mode with cached models
