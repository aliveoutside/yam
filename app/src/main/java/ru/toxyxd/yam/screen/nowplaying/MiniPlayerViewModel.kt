package ru.toxyxd.yam.screen.nowplaying

import android.annotation.SuppressLint
import android.media.audiofx.Visualizer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.toxyxd.player.MediaServiceHandler

class MiniPlayerViewModel(
    val mediaServiceHandler: MediaServiceHandler
) : ViewModel() {
    val mediaItem = mediaServiceHandler.nowPlaying.asStateFlow()
    val isPlaying = mediaServiceHandler.isPlaying.asStateFlow()

    fun switchPlayPause() {
        if (mediaServiceHandler.player.isPlaying) {
            mediaServiceHandler.player.pause()
        } else {
            mediaServiceHandler.player.play()
        }
    }
}