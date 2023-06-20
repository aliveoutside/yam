package ru.toxyxd.item.component.tracklist

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.yaapi.dto.track.TrackDto

class YaTrackComponent(
    dto: TrackDto,
    componentContext: ComponentContext
): TrackComponent, ComponentContext by componentContext {
    override val title = dto.title
    override val artist = dto.artists?.joinToString(", ") { it.name } ?: ""
    override val cover = ""
}