package ru.toxyxd.item.component.tracklist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.item.ItemRootComponent
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.item.component.TrackListComponent
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yaapi.dto.track.TrackDto

class YaTrackListComponent(
    tracksDto: Value<List<TrackDto>>,
    type: Value<ItemRootComponent.Type>,
    private val playedFromId: Value<String>,
    private val onPlayerEvent: (PlayerComponent.Event) -> Unit,
    componentContext: ComponentContext
) : TrackListComponent, KoinComponent, ComponentContext by componentContext,
    CoroutineScope by componentContext.componentCoroutineScope() {
    override val tracks: MutableValue<List<TrackComponent>> = MutableValue(emptyList())
    override fun play(index: Int) {
        val event = PlayerComponent.Event.Play(
            tracks.value,
            index,
            playedFromId.value
        )
        onPlayerEvent(event)
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