package ru.toxyxd.yam.screen.nowplaying.fullscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    val dominantColorAsBg by animateColorAsState(
        viewModel.dominantColor
    )

    Canvas(modifier) {
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    dominantColorAsBg,
                    Color.Black
                ),
                center = Offset(
                    x = size.width * 0.2f,
                    y = size.height * 0.66f
                ),
                radius = size.width * 1.3f
            )
        )
    }
}