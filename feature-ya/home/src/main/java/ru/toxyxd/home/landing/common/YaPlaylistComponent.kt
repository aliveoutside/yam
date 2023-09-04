package ru.toxyxd.home.landing.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.CoverUtil
import ru.toxyxd.common.HasId
import ru.toxyxd.common.HasIdComponent
import ru.toxyxd.home.component.common.PlaylistComponent
import ru.toxyxd.yaapi.dto.playlist.BasePlaylistDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

open class YaPlaylistComponent(
    dto: BasePlaylistDto,
    private val onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : PlaylistComponent, HasIdComponent, ComponentContext by componentContext, HasId by dto {
    override val uid: String = dto.uid
    override val kind: String = dto.kind

    override val title: String = dto.title
    override val subtitle: String? = dto.description
    override val cover: String = CoverUtil.getLargeCover(dto.cover!!.uri!!)

    override fun onClick() {
        onItemClicked(YaApiEntrypoint.YaPlaylistEntrypoint(uid, kind))
    }
}