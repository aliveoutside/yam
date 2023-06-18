package ru.toxyxd.home.catalog.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.HasId
import ru.toxyxd.home.component.common.AlbumComponent
import ru.toxyxd.yaapi.dto.album.AlbumDto

class YaRecentAlbumComponent(
    dto: AlbumDto,
    componentContext: ComponentContext
): AlbumComponent, ComponentContext by componentContext, HasId by dto {
    override val title = dto.title
    override val artist = dto.artists.firstOrNull()?.name ?: ""
    override val cover = "https://" + dto.coverUri!!.replace("%%", "700x700")
}