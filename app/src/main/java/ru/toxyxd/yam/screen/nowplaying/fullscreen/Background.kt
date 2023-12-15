package ru.toxyxd.yam.screen.nowplaying.fullscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ru.toxyxd.yam.screen.nowplaying.NowPlayingViewModel

@Composable
fun NowPlayingBackground(
    viewModel: NowPlayingViewModel,
    modifier: Modifier
) {
    val dominantColorAsBg = animateColorAsState(
        viewModel.dominantColor
    )
    val isSystemInDarkTheme = isSystemInDarkTheme()
    Canvas(modifier) {
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    dominantColorAsBg.value,
                    if (isSystemInDarkTheme) Color.Black else Color.White
                ),
                center = Offset(
                    x = size.width * 0.2f,
                    y = size.height * 0.55f
                ),
                radius = size.width * 1.3f
            )
        )
    }
}