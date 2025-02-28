# Speech Recognition Project

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
