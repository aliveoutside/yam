package ru.toxyxd.home.landing.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.CoverUtil
import ru.toxyxd.common.HasId
import ru.toxyxd.home.component.common.ArtistComponent
import ru.toxyxd.yaapi.dto.artist.ArtistDto

class YaRecentArtistComponent(
    private val dto: ArtistDto,
    private val onArtistClicked: (String) -> Unit,
    componentContext: ComponentContext
) : ArtistComponent, ComponentContext by componentContext, HasId by dto {
    override val name = dto.name
    override val image: String = CoverUtil.getLargeCover(dto.cover!!.uri!!)

    override fun onClick() {
        onArtistClicked(dto.id)
    }
}