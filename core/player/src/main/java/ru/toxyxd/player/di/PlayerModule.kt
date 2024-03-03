package ru.toxyxd.player.di

import android.annotation.SuppressLint
import android.os.Handler
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.metadata.MetadataOutput
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.text.TextOutput
import androidx.media3.exoplayer.video.VideoRendererEventListener
import androidx.media3.extractor.Extractor
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.extractor.mp3.Mp3Extractor
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
        val mp3MediaSourceFactory = ExtractorsFactory {
            arrayOf<Extractor>(Mp3Extractor())
        }

        val audioOnlyRenderersFactory =
            RenderersFactory {
                    handler: Handler,
                    _: VideoRendererEventListener,
                    audioListener: AudioRendererEventListener,
                    _: TextOutput,
                    _: MetadataOutput,
                ->
                arrayOf<Renderer>(
                    MediaCodecAudioRenderer(get(), MediaCodecSelector.DEFAULT, handler, audioListener)
                )
            }

        ExoPlayer.Builder(get())
            .setAudioAttributes(get(), true)
            .setHandleAudioBecomingNoisy(true)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(dataSourceFactory, mp3MediaSourceFactory)
            )
            .setRenderersFactory(audioOnlyRenderersFactory)
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