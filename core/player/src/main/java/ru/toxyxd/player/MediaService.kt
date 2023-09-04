package ru.toxyxd.player

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class MediaService : MediaSessionService(), KoinComponent {
    private val mediaSession by inject<MediaSession>()

    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val sessionToken = SessionToken(this, ComponentName(this, MediaService::class.java))
        MediaController.Builder(this, sessionToken).buildAsync()

        return START_NOT_STICKY
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession =
        mediaSession

    override fun onDestroy() {
        Log.v("MediaService", "destroyed")
        mediaSession.run {
            player.release()
            release()
        }
        super.onDestroy()
    }
}