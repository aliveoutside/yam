package ru.toxyxd.item.component.tracklist

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent
import ru.toxyxd.common.CoverUtil
import ru.toxyxd.item.component.AlbumTrackComponent
import ru.toxyxd.item.component.PlaylistTrackComponent
import ru.toxyxd.item.component.TopTrackComponent
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.yaapi.dto.track.TrackDto

sealed class YaTrackComponent(
    dto: TrackDto,
    componentContext: ComponentContext
) : TrackComponent, KoinComponent, ComponentContext by componentContext {
    override val id = dto.id
    override val title = dto.title
    override val artists = dto.artists?.map { it.name } ?: listOf("")
    override val artistsIds = dto.artists?.map { it.id } ?: listOf("")
    override val albumId = dto.albums?.firstOrNull()?.id
    override val cover = dto.coverUri?.let { CoverUtil.getSmallCover(it) }
    override val hugeCover = dto.coverUri?.let { CoverUtil.getLargeCover(it) }
    override val duration: Long = dto.durationMs
}

class YaTopTrackComponent(
    dto: TrackDto,
    override val index: Int,
    componentContext: ComponentContext
) : YaTrackComponent(dto, componentContext), TopTrackComponent

class YaPlaylistTrackComponent(dto: TrackDto, componentContext: ComponentContext) :
    YaTrackComponent(dto, componentContext), PlaylistTrackComponent

class YaAlbumTrackComponent(
    dto: TrackDto,
    override val index: Int,
    componentContext: ComponentContext
) : YaTrackComponent(dto, componentContext), AlbumTrackComponent