package ru.toxyxd.yam.screen.nowplaying

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.asStateFlow
import ru.toxyxd.player.MediaServiceHandler

class NowPlayingViewModel(
    val mediaServiceHandler: MediaServiceHandler
) : ViewModel() {
    val mediaItem = mediaServiceHandler.nowPlaying.asStateFlow()
    val isPlaying = mediaServiceHandler.isPlaying.asStateFlow()
    val progress get() = mediaServiceHandler.progressFlow
    val duration get() = mediaServiceHandler.duration.coerceAtLeast(0L)

    var dominantColor by mutableStateOf(Color.Transparent)

    fun switchPlayPause() {
        if (mediaServiceHandler.player.isPlaying) {
            mediaServiceHandler.player.pause()
        } else {
            mediaServiceHandler.player.play()
        }
    }

    fun seekTo(position: Long) {
        mediaServiceHandler.player.seekTo(position)
    }
}