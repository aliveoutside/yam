package ru.toxyxd.home.landing.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.yaapi.dto.playlist.BasePlaylistDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaRecentPlaylistComponent(
    dto: BasePlaylistDto,
    onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : YaPlaylistComponent(dto, onItemClicked, componentContext) {
    override val subtitle: String? = null
}