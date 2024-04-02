package ru.toxyxd.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaPlayerComponent(
    eventFlow: SharedFlow<PlayerComponent.Event>,
    private val onArtistClicked: (String) -> Unit,
    private val onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : PlayerComponent, KoinComponent, ComponentContext by componentContext,
    CoroutineScope by componentContext.componentCoroutineScope() {
    private val mediaServiceHandler by inject<MediaServiceHandler>()
    private val currentQueue = ArrayList<TrackComponent>()

    override val nowPlaying: MutableStateFlow<TrackComponent?> = MutableStateFlow(null)
    override val isPlaying: MutableStateFlow<Boolean> = mediaServiceHandler.isPlaying
    override val progressFlow: Flow<Long> = mediaServiceHandler.progressFlow.shareIn(this, SharingStarted.Eagerly)
    override var duration: Long = 0

    init {
        get<Context>().also {
            it.startService(Intent(it, MediaService::class.java))
        }

        initFlows(eventFlow)
    }

    override fun onArtistClicked() {
        nowPlaying.value?.let {
            onArtistClicked(it.artistsIds.first())
        }
    }

    override fun onAlbumClicked() {
        nowPlaying.value?.let {
            onItemClicked(YaApiEntrypoint.YaAlbumEntrypoint(it.albumId ?: return))
        }
    }

    override fun onInputEvent(event: PlayerComponent.Event) {
        when (event) {
            is PlayerComponent.Event.Play -> play(
                event.mediaItems,
                event.position,
                event.playedFromId
            )

            is PlayerComponent.Event.PlayPause -> {
                if (isPlaying.value) {
                    mediaServiceHandler.player.pause()
                } else {
                    mediaServiceHandler.player.play()
                }
            }

            is PlayerComponent.Event.Next -> next()
            is PlayerComponent.Event.Previous -> previous()
            is PlayerComponent.Event.SeekTo -> seekTo(event.position)
        }
    }

    override fun play() {
        mediaServiceHandler.player.play()
    }

    override fun play(mediaItems: List<TrackComponent>, position: Int, playedFromId: String) {
        currentQueue.clear()
        currentQueue.addAll(mediaItems)

        val extrasBundle = Bundle().apply {
            putString("playedFromId", playedFromId)
        }

        val metadatas = mediaItems.map { track ->
            MediaMetadata.Builder().setTitle(track.title).setArtist(track.artists.joinToString(", "))
                .setExtras(extrasBundle)
                .setArtworkUri(track.hugeCover?.let { Uri.parse(it) }).build()
        }

        mediaServiceHandler.play(metadatas.mapIndexed { metaIndex, mediaMetadata ->
            MediaItem.Builder()
                .setMediaId(mediaItems[metaIndex].id)
                .setMediaMetadata(mediaMetadata)
                .setUri(mediaItems[metaIndex].id)
                .build()
        }, position)
    }
    override fun pause() {
        mediaServiceHandler.player.pause()
    }

    override fun next() {
        mediaServiceHandler.player.seekToNextMediaItem()
    }

    override fun previous() {
        mediaServiceHandler.player.seekToPreviousMediaItem()
    }

    override fun seekTo(position: Long) {
        mediaServiceHandler.player.seekTo(position)
    }

    private fun initFlows(eventFlow: SharedFlow<PlayerComponent.Event>) {
        eventFlow.onEach {
            onInputEvent(it)
        }.catch {
            Log.e("YaPlayerComponent", "Error in eventFlow: $it")
        }.launchIn(this)

        mediaServiceHandler.nowPlaying.onEach { serviceTrack ->
            nowPlaying.emit(serviceTrack?.let { track ->
                currentQueue.find { it.id == track.mediaId }
            })
        }.catch {
            Log.e("YaPlayerComponent", "Error in nowPlaying: $it")
        }.launchIn(this)

        nowPlaying.onEach {
            duration = it?.duration ?: 0
        }.launchIn(this)
    }
}