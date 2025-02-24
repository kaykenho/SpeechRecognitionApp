import os
import librosa
import numpy as np

# Path to the LibriSpeech dataset
DATASET_PATH = 'C:/Users/kayky/PycharmProjects/SpeechRecognitionApp-PY/data'


# A function to load and preprocess the audio (extracting the MFCCs) and corresponding transcripts
def load_librispeech_data(directory, max_mfcc_length=130):
    audio_files = []
    transcripts = []

    # Traverse the directory to find .flac files and .trans.txt files
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith('.flac'):
                # Get audio file path
                audio_path = os.path.join(root, file)
                # Load the file
                audio, sr = librosa.load(audio_path, sr=None)  # using the original sampling rate
                # Extract the MFCC features from the audio
                mfcc = librosa.feature.mfcc(y=audio, sr=sr, n_mfcc=13)  # defining 13 MFCCs as features

                # Pad or truncate the MFCC to ensure a consistent length
                if mfcc.shape[1] < max_mfcc_length:
                    pad_width = max_mfcc_length - mfcc.shape[1]
                    mfcc = np.pad(mfcc, ((0, 0), (0, pad_width)), mode='constant')
                elif mfcc.shape[1] > max_mfcc_length:
                    mfcc = mfcc[:, :max_mfcc_length]

                audio_files.append(mfcc)

                # Get the base name of the audio file (excluding the '-0000')
                base_name = file.replace('.flac', '').split('-')[0] + '-' + file.replace('.flac', '').split('-')[1]

                # Search for the corresponding transcript file
                transcript_path = os.path.join(root, base_name + '.trans.txt')  # Match transcript without the -0000

                # Check if the transcript file exists
                if os.path.exists(transcript_path):
                    with open(transcript_path, 'r') as f:
                        transcript = f.read().strip()  # Read the transcript text
                        transcripts.append(transcript)
                else:
                    print(f"Warning: Transcript file {transcript_path} not found.")

    return audio_files, transcripts


# A function to normalize the MFCC features
def normalize_mfcc(mfcc_features):
    return (mfcc_features - np.mean(mfcc_features)) / np.std(mfcc_features)


# Example usage
if __name__ == '__main__':
    # Load the training data (this might take time depending on dataset size)
    train_audio, train_transcripts = load_librispeech_data(os.path.join(DATASET_PATH, 'train-clean-360'))

    # Normalize MFCC features
    normalized_audio = [normalize_mfcc(mfcc) for mfcc in train_audio]

    # Print example outputs
    print(f"First audio MFCC shape: {normalized_audio[0].shape}")
    print(f"First transcript: {train_transcripts[0]}")
