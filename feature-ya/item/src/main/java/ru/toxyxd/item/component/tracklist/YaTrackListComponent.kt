package ru.toxyxd.item.component.tracklist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.toxyxd.item.ItemRootComponent
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.item.component.TrackListComponent
import ru.toxyxd.yaapi.dto.track.TrackDto

class YaTrackListComponent(
    tracksDto: Value<List<TrackDto>>,
    type: Value<ItemRootComponent.Type>,
    componentContext: ComponentContext
) : TrackListComponent, ComponentContext by componentContext {
    override val tracks: MutableValue<List<TrackComponent>> = MutableValue(emptyList())

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