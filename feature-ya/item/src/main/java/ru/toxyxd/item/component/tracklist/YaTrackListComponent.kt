package ru.toxyxd.item.component.tracklist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.item.component.TrackListComponent
import ru.toxyxd.yaapi.dto.track.TrackDto

class YaTrackListComponent(
    tracksDto: Value<List<TrackDto>>,
    componentContext: ComponentContext
) : TrackListComponent, ComponentContext by componentContext {
    override val tracks: MutableValue<List<TrackComponent>> = MutableValue(emptyList())

    init {
        tracksDto.subscribe {
            tracks.value = it.mapIndexed { index, trackDto -> YaTrackComponent(trackDto, childContext("track${index}")) }
        }
    }
}