package ru.toxyxd.home.landing.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.CoverUtil
import ru.toxyxd.common.HasId
import ru.toxyxd.home.component.common.AlbumComponent
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

open class YaAlbumComponent(
    dto: AlbumDto,
    private val onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : AlbumComponent, ComponentContext by componentContext, HasId by dto {
    override val id = dto.id
    override val title = dto.title
    override val artist = dto.artists.firstOrNull()?.name ?: ""
    override val type: String? = dto.type
    override val cover = CoverUtil.getLargeCover(dto.coverUri!!)

    override fun onClick() {
        onItemClicked(YaApiEntrypoint.YaAlbumEntrypoint(id))
    }
}