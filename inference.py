import tensorflow as tf
import librosa
import numpy as np
from preprocessing.data_loader import normalize_mfcc

# loading the trained and quantized model
model = tf.keras.models.load_model('quantized_model.h5')

# a function for performing inference on new audio
def predict_audio(audio_path):
    # loading and preprocessing the data
    audio, sr = librosa.load(audio_path, sr=None)
    mfcc = librosa.feature.mfcc(audio, sr=sr, n_mfcc=13)
    mfcc = normalize_mfcc(mfcc)

    # reshaping the mfcc to match the model input
    mfcc = np.expand_dims(mfcc, axis=0)

    # performing the prediction
    prediction = model.predict(mfcc)

    return prediction


audio_file = 'path to test_audio.flac'
prediction = predict_audio(audio_file)
print('Predicted output:', prediction)