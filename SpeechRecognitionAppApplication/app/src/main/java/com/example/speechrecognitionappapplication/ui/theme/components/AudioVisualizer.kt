package com.example.speechrecognitionappapplication.ui.theme.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun AudioVisualizer(
    amplitudes: List<Float>,
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF6200EE)
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val barWidth = canvasWidth / (amplitudes.size * 2f)
        val maxAmplitude = amplitudes.maxOrNull() ?: 1f

        amplitudes.forEachIndexed { index, amplitude ->
            val normalizedAmplitude = (amplitude / maxAmplitude).coerceIn(0.1f, 1f)
            val animatedHeight by animateFloatAsState(
                targetValue = canvasHeight * normalizedAmplitude,
                animationSpec = tween(100),
                label = "barHeight"
            )

            val startX = index * (barWidth * 2) + barWidth / 2
            val startY = canvasHeight / 2 - animatedHeight / 2
            val endY = canvasHeight / 2 + animatedHeight / 2

            drawLine(
                color = barColor,
                start = Offset(startX, startY),
                end = Offset(startX, endY),
                strokeWidth = barWidth * 0.8f,
                cap = StrokeCap.Round
            )
        }
    }
}