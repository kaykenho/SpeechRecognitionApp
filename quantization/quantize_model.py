import tensorflow as tf
import numpy as np
import os
import argparse
from tensorflow.keras.models import load_model


def quantize_model(model_path="speech_recognition_model_h5",
                   save_path=None):

    # Check if model exists
    if not os.path.exists(model_path):
        print(f"Error: Model not found at {model_path}")
        return

    try:
        print(f"Loading model from {model_path}")
        model = load_model(model_path)
        print("Model loaded successfully.")
    except Exception as e:
        print(f"Error loading model: {e}")
        return

    # Set default save path if not provided
    if not save_path:
        model_dir = os.path.dirname(model_path)
        model_name = os.path.basename(model_path).split('.')[0]
        save_path = os.path.join(model_dir, f"{model_name}_quantized.tflite")

    # Convert the model to a TensorFlow Lite model
    converter = tf.lite.TFLiteConverter.from_keras_model(model)

    # Set the optimization flag to optimize for size
    converter.optimizations = [tf.lite.Optimize.DEFAULT]

    # Enable SELECT_TF_OPS to handle operations not natively supported in TFLite
    converter.target_spec.supported_ops = [
        tf.lite.OpsSet.TFLITE_BUILTINS,
        tf.lite.OpsSet.SELECT_TF_OPS
    ]

    # Disable tensor list lowering as mentioned in the error message
    converter._experimental_lower_tensor_list_ops = False

    print("Starting quantization process...")
    try:
        quantized_tflite_model = converter.convert()

        # Save the quantized model
        with open(save_path, 'wb') as f:
            f.write(quantized_tflite_model)

        print(f"Quantized model saved to {save_path}")

        # Calculate and print size reduction
        original_size = os.path.getsize(model_path) / (1024 * 1024)
        quantized_size = os.path.getsize(save_path) / (1024 * 1024)
        reduction = (1 - quantized_size / original_size) * 100
        print(f"Original model size: {original_size:.2f} MB")
        print(f"Quantized model size: {quantized_size:.2f} MB")
        print(f"Size reduction: {reduction:.2f}%")

        # Note about the SELECT_TF_OPS dependency
        print("\nNOTE: This quantized model uses SELECT_TF_OPS and requires the TensorFlow runtime")
        print("      when deploying. For Android, you'll need to include the TensorFlow Lite")
        print("      Support Library in your app's dependencies.")

    except Exception as e:
        print(f"Error during quantization: {e}")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Quantize a TensorFlow model')
    parser.add_argument('--model_path', type=str,
                        default="speech_recognition_model_h5",
                        help='Path to the saved model file (.h5)')
    parser.add_argument('--save_path', type=str,
                        help='Path to save the quantized model file (.tflite)')

    args = parser.parse_args()

    quantize_model(args.model_path, args.save_path)
