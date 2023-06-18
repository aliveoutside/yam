package ru.toxyxd.home.catalog.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.yaapi.dto.playlist.BasePlaylistDto

class YaRecentPlaylistComponent(
    dto: BasePlaylistDto,
    componentContext: ComponentContext
) : YaPlaylistComponent(dto, componentContext) {
    override val subtitle: String? = null
}