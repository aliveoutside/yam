package ru.toxyxd.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MediaServiceHandler : Player.Listener, KoinComponent {
    private val mediaSession by inject<MediaSession>()
    private val player = mediaSession.player

    init {
        player.addListener(this)
    }

    fun addAndPlay(mediaItems: List<MediaItem>, position: Int = 0) {
        player.setMediaItems(mediaItems)
        player.seekTo(position, 0)
        player.prepare()
        player.play()
    }
}