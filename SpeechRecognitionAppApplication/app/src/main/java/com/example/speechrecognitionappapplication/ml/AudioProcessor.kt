import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

@Singleton
class AudioProcessor @Inject constructor() {
    companion object {
        private const val TAG = "AudioProcessor"
        private const val SAMPLE_RATE = 16000
        private const val FRAME_SIZE = 512
        private const val FRAME_STRIDE = 256
        private const val NUM_MFCC = 13
        private const val NUM_FFT = 512
        private const val LOW_FREQ = 20
        private const val HIGH_FREQ = 8000
        private const val PREEMPHASIS_COEFF = 0.97f
    }

    /**
     * Extract MFCC features from raw audio data
     */
    fun extractFeatures(audioData: ShortArray): FloatArray {
        try {
            // Step 1: Convert to float and apply pre-emphasis
            val preemphasized = applyPreemphasis(audioData)

            // Step 2: Frame the signal
            val frames = frame(preemphasized)

            // Step 3: Apply window function
            val windowed = applyWindow(frames)

            // Step 4: Compute FFT
            val fftResults = computeFFT(windowed)

            // Step 5: Compute power spectrum
            val powerSpectrum = computePowerSpectrum(fftResults)

            // Step 6: Apply mel filterbank
            val melFilterbankFeatures = applyMelFilterbank(powerSpectrum)

            // Step 7: Compute MFCC
            val mfcc = computeMFCC(melFilterbankFeatures)

            // Step 8: Normalize features
            return normalizeFeatures(mfcc)

        } catch (e: Exception) {
            Log.e(TAG, "Error extracting features: ${e.message}")
            // Return empty array in case of error
            return FloatArray(NUM_MFCC * (audioData.size / FRAME_STRIDE))
        }
    }

    /**
     * Convert audio data to float and apply pre-emphasis filter
     */
    private fun applyPreemphasis(audioData: ShortArray): FloatArray {
        val result = FloatArray(audioData.size)

        // Convert to float (range -1 to 1)
        for (i in audioData.indices) {
            result[i] = audioData[i] / 32768.0f
        }

        // Apply pre-emphasis
        for (i in audioData.size - 1 downTo 1) {
            result[i] = result[i] - PREEMPHASIS_COEFF * result[i - 1]
        }

        return result
    }

    /**
     * Frame the signal into overlapping frames
     */
    private fun frame(signal: FloatArray): Array<FloatArray> {
        val frameLength = FRAME_SIZE
        val frameStep = FRAME_STRIDE

        val signalLength = signal.size
        val numFrames = ceil(abs(signalLength - frameLength) / frameStep.toFloat()).toInt() + 1

        val frames = Array(numFrames) { FloatArray(frameLength) }

        for (i in 0 until numFrames) {
            val startIdx = i * frameStep
            val endIdx = min(startIdx + frameLength, signalLength)

            // Copy signal data to frame
            signal.copyInto(frames[i], 0, startIdx, endIdx)

            // Zero padding if needed
            if (endIdx < startIdx + frameLength) {
                for (j in (endIdx - startIdx) until frameLength) {
                    frames[i][j] = 0f
                }
            }
        }

        return frames
    }

    /**
     * Apply Hamming window to each frame
     */
    private fun applyWindow(frames: Array<FloatArray>): Array<FloatArray> {
        val frameLength = frames[0].size
        val windowFunction = FloatArray(frameLength)

        // Precompute Hamming window
        for (i in 0 until frameLength) {
            windowFunction[i] = 0.54f - 0.46f * cos(2 * PI * i / (frameLength - 1)).toFloat()
        }

        // Apply window to each frame
        val windowedFrames = Array(frames.size) { FloatArray(frameLength) }
        for (i in frames.indices) {
            for (j in 0 until frameLength) {
                windowedFrames[i][j] = frames[i][j] * windowFunction[j]
            }
        }

        return windowedFrames
    }

    /**
     * Compute FFT for each frame
     * Note: This is a simple implementation. In a real app, use an optimized FFT library.
     */
    private fun computeFFT(frames: Array<FloatArray>): Array<FloatArray> {
        // For a full implementation, use a proper FFT library
        // This is a placeholder that would be replaced with actual FFT computation

        // In a real implementation, you would use a library like JTransforms
        // or implement the FFT algorithm properly

        // For now, we'll return a simple placeholder
        return frames
    }

    /**
     * Compute power spectrum from FFT results
     */
    private fun computePowerSpectrum(fftResults: Array<FloatArray>): Array<FloatArray> {
        val numFrames = fftResults.size
        val fftSize = fftResults[0].size
        val powerSize = fftSize / 2 + 1

        val powerSpectrum = Array(numFrames) { FloatArray(powerSize) }

        for (i in 0 until numFrames) {
            for (j in 0 until powerSize) {
                // In a real FFT implementation, you would compute:
                // power = (real^2 + imag^2) / fftSize

                // Placeholder calculation - would be replaced with actual implementation
                powerSpectrum[i][j] = fftResults[i][j] * fftResults[i][j]
            }
        }

        return powerSpectrum
    }

    /**
     * Apply mel filterbank to power spectrum
     */
    private fun applyMelFilterbank(powerSpectrum: Array<FloatArray>): Array<FloatArray> {
        val numFrames = powerSpectrum.size
        val numFilters = NUM_MFCC * 2  // Typically 2x the number of MFCCs

        // Create mel filterbank (would be precomputed in practice)
        val filterbank = createMelFilterbank(NUM_FFT, SAMPLE_RATE, numFilters)

        val filteredFeatures = Array(numFrames) { FloatArray(numFilters) }

        for (i in 0 until numFrames) {
            for (j in 0 until numFilters) {
                var sum = 0.0f
                for (k in powerSpectrum[i].indices) {
                    sum += powerSpectrum[i][k] * filterbank[j][k]
                }
                filteredFeatures[i][j] = sum

                // Apply log to simulate log mel spectrum
                if (filteredFeatures[i][j] > 0) {
                    filteredFeatures[i][j] = ln(filteredFeatures[i][j]).toFloat()
                } else {
                    filteredFeatures[i][j] = 0f
                }
            }
        }

        return filteredFeatures
    }

    /**
     * Create mel filterbank
     */
    private fun createMelFilterbank(nfft: Int, sampleRate: Int, numFilters: Int): Array<FloatArray> {
        // Convert Hz to mel
        fun hzToMel(hz: Float): Float {
            return 2595 * log10(1 + hz / 700).toFloat()
        }

        // Convert mel to Hz
        fun melToHz(mel: Float): Float {
            return 700 * (10f.pow(mel / 2595) - 1)
        }

        val lowMel = hzToMel(LOW_FREQ.toFloat())
        val highMel = hzToMel(HIGH_FREQ.toFloat())

        // Create equally spaced points in mel scale
        val melPoints = FloatArray(numFilters + 2)
        for (i in melPoints.indices) {
            melPoints[i] = lowMel + i * (highMel - lowMel) / (numFilters + 1)
        }

// Convert mel points back to Hz
        val hzPoints = FloatArray(numFilters + 2)
        for (i in hzPoints.indices) {
            hzPoints[i] = melToHz(melPoints[i])
        }

        // Convert Hz points to FFT bin indices
        val bins = FloatArray(numFilters + 2)
        for (i in bins.indices) {
            bins[i] = floor(nfft * hzPoints[i] / sampleRate).toInt().toFloat()
        }

        // Create triangular filters
        val fbank = Array(numFilters) { FloatArray(nfft / 2 + 1) }
        for (i in 0 until numFilters) {
            for (j in 0 until (nfft / 2 + 1)) {
                fbank[i][j] = 0f

                if (j > bins[i] && j < bins[i + 1]) {
                    fbank[i][j] = (j - bins[i]) / (bins[i + 1] - bins[i])
                } else if (j >= bins[i + 1] && j < bins[i + 2]) {
                    fbank[i][j] = (bins[i + 2] - j) / (bins[i + 2] - bins[i + 1])
                }
            }
        }

        return fbank
    }

    /**
     * Compute MFCC from mel filterbank features
     */
    private fun computeMFCC(melFilterbankFeatures: Array<FloatArray>): Array<FloatArray> {
        val numFrames = melFilterbankFeatures.size
        val numFilters = melFilterbankFeatures[0].size

        val mfcc = Array(numFrames) { FloatArray(NUM_MFCC) }

        for (i in 0 until numFrames) {
            for (j in 0 until NUM_MFCC) {
                var sum = 0.0f
                for (k in 0 until numFilters) {
                    // Apply DCT-II
                    sum += melFilterbankFeatures[i][k] * cos(PI * j * (k + 0.5) / numFilters).toFloat()
                }
                mfcc[i][j] = sum
            }
        }

        return mfcc
    }

    /**
     * Normalize features for better model performance
     */
    private fun normalizeFeatures(features: Array<FloatArray>): FloatArray {
        val numFrames = features.size
        val numFeatures = features[0].size

        // Flatten 2D array to 1D
        val flatFeatures = FloatArray(numFrames * numFeatures)

        // Calculate mean and standard deviation
        var sum = 0.0f
        var sumSquared = 0.0f
        var count = 0

        for (i in 0 until numFrames) {
            for (j in 0 until numFeatures) {
                val value = features[i][j]
                val index = i * numFeatures + j
                flatFeatures[index] = value

                sum += value
                sumSquared += value * value
                count++
            }
        }

        val mean = sum / count
        val variance = (sumSquared / count) - (mean * mean)
        val stdDev = sqrt(variance.coerceAtLeast(1e-10f))

        // Normalize
        for (i in flatFeatures.indices) {
            flatFeatures[i] = (flatFeatures[i] - mean) / stdDev
        }

        return flatFeatures
    }
}