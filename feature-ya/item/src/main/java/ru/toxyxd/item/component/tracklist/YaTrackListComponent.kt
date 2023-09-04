package ru.toxyxd.item.component.tracklist

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.item.ItemRootComponent
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.item.component.TrackListComponent
import ru.toxyxd.player.MediaServiceHandler
import ru.toxyxd.yaapi.dto.track.TrackDto

class YaTrackListComponent(
    tracksDto: Value<List<TrackDto>>,
    type: Value<ItemRootComponent.Type>,
    componentContext: ComponentContext
) : TrackListComponent, KoinComponent, ComponentContext by componentContext,
    CoroutineScope by componentContext.componentCoroutineScope() {
    private val mediaServiceHandler by inject<MediaServiceHandler>()

    override val tracks: MutableValue<List<TrackComponent>> = MutableValue(emptyList())
    override fun play(index: Int) {
        val metadatas = tracks.value.map {
            MediaMetadata.Builder().setTitle(it.title).setArtist(it.artist)
                .setArtworkUri(Uri.parse(it.hugeCover)).build()
        }

        mediaServiceHandler.addAndPlay(metadatas.mapIndexed { metaIndex, mediaMetadata ->
            MediaItem.Builder()
                .setMediaId(tracks.value[metaIndex].id)
                .setMediaMetadata(mediaMetadata)
                .setUri(tracks.value[metaIndex].id)
                .build()
        }, index)
    }

    init {
        tracksDto.subscribe {
            tracks.value = it.mapIndexed { index, trackDto ->
                when (type.value) {
                    ItemRootComponent.Type.PLAYLIST -> YaPlaylistTrackComponent(
                        trackDto,
                        childContext("track${index}")
                    )

                    ItemRootComponent.Type.ALBUM -> YaAlbumTrackComponent(
                        trackDto,
                        index,
                        childContext("track${index}")
                    )
                }
            }
        }
    }
}