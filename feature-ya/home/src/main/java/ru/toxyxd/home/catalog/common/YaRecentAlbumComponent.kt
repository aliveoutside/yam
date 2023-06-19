package ru.toxyxd.home.catalog.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.yaapi.dto.album.AlbumDto

class YaRecentAlbumComponent(
    dto: AlbumDto,
    componentContext: ComponentContext
): YaAlbumComponent(dto, componentContext) {
    override val type: String? = null
}