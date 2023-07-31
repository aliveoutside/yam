package ru.toxyxd.home.catalog.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaRecentAlbumComponent(
    dto: AlbumDto,
    onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : YaAlbumComponent(dto, onItemClicked, componentContext) {
    override val type: String? = null
}