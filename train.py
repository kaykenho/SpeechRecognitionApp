import tensorflow as tf
from models.model import build_model
import os
from preprocessing.data_loader import load_librispeech_data, normalize_mfcc
from quantization.quantize_model import quantize_model, save_quantized_model
import numpy as np
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences

DATASET_PATH = 'C:/Users/kayky/PycharmProjects/SpeechRecognitionApp-PY/data'

# Loading the data
train_audio, train_transcripts = load_librispeech_data(os.path.join(DATASET_PATH, 'train-clean-360'))
train_audio = [normalize_mfcc(mfcc) for mfcc in train_audio]

# Convert to numpy arrays for training
train_audio = np.array([np.transpose(mfcc) for mfcc in train_audio])  # Shape (samples, time_steps, features)

# Ensure that the transcripts are tokenized
tokenizer = Tokenizer(char_level=False)  # Set char_level=True if you want to tokenize at the character level
tokenizer.fit_on_texts(train_transcripts)

# Convert transcripts to sequences of integers
train_transcripts_seq = tokenizer.texts_to_sequences(train_transcripts)

# Pad the sequences to ensure consistent length (set maxlen based on the max length of your transcripts or choose a fixed length)
max_seq_length = 100  # Example, adjust based on your data
train_transcripts_seq = pad_sequences(train_transcripts_seq, maxlen=max_seq_length, padding='post', dtype='float32')

# Print the shapes to confirm everything is correct
print(f"train_audio shape: {train_audio.shape}")
print(f"train_transcripts_seq shape: {train_transcripts_seq.shape}")

# Build the model
model = build_model(input_shape=(train_audio.shape[1], train_audio.shape[2]))

# Train the model
model.fit(train_audio, train_transcripts_seq, epochs=10, batch_size=32)

# Save the trained model
model.save('speech_recognition_model_h5')
