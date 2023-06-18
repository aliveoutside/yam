package ru.toxyxd.home.catalog.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.HasId
import ru.toxyxd.common.HasIdComponent
import ru.toxyxd.home.component.common.PlaylistComponent
import ru.toxyxd.yaapi.dto.playlist.BasePlaylistDto

open class YaPlaylistComponent(
    dto: BasePlaylistDto,
    componentContext: ComponentContext
) : PlaylistComponent, HasIdComponent, ComponentContext by componentContext, HasId by dto {
    override val title: String = dto.title
    override val subtitle: String? = dto.description
    override val cover: String =
        "https://" + dto.cover!!.uri!!.replace("%%", "700x700")
}