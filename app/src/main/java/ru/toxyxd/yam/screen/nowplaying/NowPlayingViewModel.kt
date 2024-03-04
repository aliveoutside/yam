package ru.toxyxd.yam.screen.nowplaying

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import kotlinx.coroutines.flow.asStateFlow
import ru.toxyxd.player.MediaServiceHandler
import ru.toxyxd.yam.ext.getSurfaceColor

class NowPlayingViewModel(
    val mediaServiceHandler: MediaServiceHandler,
) : ViewModel() {
    val mediaItem = mediaServiceHandler.nowPlaying.asStateFlow()
    val isPlaying = mediaServiceHandler.isPlaying.asStateFlow()
    val progress get() = mediaServiceHandler.progressFlow
    val duration get() = mediaServiceHandler.duration.coerceAtLeast(0L)

    var dominantColor by mutableStateOf(Color.Transparent)

    fun extractColor(bitmap: Bitmap, defaultSurfaceColor: Color) {
        Palette
            .from(bitmap)
            .clearFilters()
            .generate {
                val color = it?.getSurfaceColor() ?: defaultSurfaceColor.toArgb()
                dominantColor = Color(color)
            }
    }

    fun switchPlayPause() {
        if (mediaServiceHandler.player.isPlaying) {
            mediaServiceHandler.player.pause()
        } else {
            mediaServiceHandler.player.play()
        }
    }

    fun next() {
        mediaServiceHandler.player.seekToNextMediaItem()
    }

    fun previous() {
        mediaServiceHandler.player.seekToPreviousMediaItem()
    }

    fun seekTo(position: Long) {
        mediaServiceHandler.player.seekTo(position)
    }
}