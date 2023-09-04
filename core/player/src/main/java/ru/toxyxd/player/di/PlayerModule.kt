package ru.toxyxd.player.di

import android.annotation.SuppressLint
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.MediaSession
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.toxyxd.player.MediaService
import ru.toxyxd.player.MediaServiceHandler
import ru.toxyxd.player.YaDataSpecResolver
import ru.toxyxd.player.YaUriResolver

@SuppressLint("UnsafeOptInUsageError")
val playerModule = module {
    single {
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    single {
        val resolver = YaUriResolver(get())
        val dataSourceFactory = ResolvingDataSource.Factory(
            DefaultHttpDataSource.Factory(),
            YaDataSpecResolver(resolver)
        )

        ExoPlayer.Builder(get())
            .setAudioAttributes(get(), true)
            .setHandleAudioBecomingNoisy(true)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(dataSourceFactory)
            )
            .build()
    } bind Player::class

    single {
        MediaSession.Builder(get(), get()).build()
    }

    single {
        MediaService()
    }

    single {
        MediaServiceHandler()
    }
}