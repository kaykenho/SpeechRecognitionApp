import os
import librosa
import numpy as np
from tensorflow.keras.preprocessing.sequence import pad_sequences

# Path to the librispeech dataset
DATASET_PATH = 'C:/Users/kayky/PycharmProjects/SpeechRecognitionApp-PY/data'


# A function to load and preprocess the audio (extracting the mfccs) and corresponding the transcripts
def load_librispeech_data(directory):
    audio_files = []
    transcripts = []

    # Traverse the directory to find the .flac files and .trans.txt files
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith('.flac'):
                # Get audio file path
                audio_path = os.path.join(root, file)
                # Load the file
                audio, sr = librosa.load(audio_path, sr=None)  # Using the original sampling rate
                # Extracting the MFCC feature from the audio
                mfcc = librosa.feature.mfcc(y=audio, sr=sr, n_mfcc=13)  # Defining 13 MFCCs as features

                # Transpose and pad the MFCC to make it have consistent time steps (100)
                mfcc = np.transpose(mfcc)  # Shape (time_steps, features)
                mfcc = pad_sequences([mfcc], maxlen=100, padding='post', dtype='float32')[0]

                audio_files.append(mfcc)

                # Getting the base name of the audio file (without -0000)
                base_name = file.replace('.flac', '').split('-')[0] + '-' + file.replace('.flac', '').split('-')[1]

                # Search for the corresponding transcript file
                transcript_path = os.path.join(root, base_name + '.trans.txt')  # Match transcript without the -0000

                # Checking if transcript exists
                if os.path.exists(transcript_path):
                    with open(transcript_path, 'r') as f:
                        transcript = f.read().strip()  # Reading the transcript text
                        transcripts.append(transcript)
                else:
                    print(f'Transcript file {transcript_path} not found.')

    return audio_files, transcripts


# A function to normalize the MFCC features
def normalize_mfcc(mfcc_features):
    return (mfcc_features - np.mean(mfcc_features)) / np.std(mfcc_features)
