package ru.toxyxd.player

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MediaServiceHandler : Player.Listener, MediaSession.Callback, KoinComponent {
    val mediaSession by inject<MediaSession>()
    val player = mediaSession.player

    var nowPlaying = MutableStateFlow(player.currentMediaItem)
    var isPlaying = MutableStateFlow(player.isPlaying)
    val progressFlow = flow {
        while (true) {
            emit(player.currentPosition)
            delay(50)
        }
    }.catch { emit(0L) }
    val duration get() = player.contentDuration

    init {
        player.addListener(this)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        nowPlaying.tryEmit(mediaItem)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        this.isPlaying.tryEmit(isPlaying)
    }


    fun play(mediaItems: List<MediaItem>, position: Int = 0) {
        if (player.currentMediaItem != null &&
            mediaItems[position].mediaMetadata.extras?.getString("playedFromId")
            == player.currentMediaItem!!.mediaMetadata.extras?.getString("playedFromId")
        ) {
            player.seekTo(position, 0)
            player.play()
            Log.d("MediaServiceHandler", "play: same playlist")
            return
        }

        player.setMediaItems(mediaItems)
        player.seekTo(position, 0)
        player.prepare()
        player.play()
        Log.d("MediaServiceHandler", "play: new playlist")
    }
}