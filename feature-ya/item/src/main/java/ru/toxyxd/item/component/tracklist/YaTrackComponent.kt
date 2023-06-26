package ru.toxyxd.item.component.tracklist

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.item.component.AlbumTrackComponent
import ru.toxyxd.item.component.PlaylistTrackComponent
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.yaapi.dto.track.TrackDto

sealed class YaTrackComponent(
    dto: TrackDto,
    componentContext: ComponentContext
) : TrackComponent, ComponentContext by componentContext {
    override val title = dto.title
    override val artist = dto.artists?.joinToString(", ") { it.name } ?: ""
    override val cover = "https://" + dto.coverUri!!.replace("%%", "100x100")
}

class YaPlaylistTrackComponent(dto: TrackDto, componentContext: ComponentContext) :
    YaTrackComponent(dto, componentContext), PlaylistTrackComponent

class YaAlbumTrackComponent(dto: TrackDto, override val index: Int, componentContext: ComponentContext) :
    YaTrackComponent(dto, componentContext), AlbumTrackComponent {
}