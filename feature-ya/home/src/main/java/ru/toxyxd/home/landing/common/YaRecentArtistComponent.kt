package ru.toxyxd.home.landing.common

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.HasId
import ru.toxyxd.home.component.common.ArtistComponent
import ru.toxyxd.yaapi.dto.artist.ArtistDto

class YaRecentArtistComponent(
    dto: ArtistDto,
    componentContext: ComponentContext
) : ArtistComponent, ComponentContext by componentContext, HasId by dto {
    override val name = dto.name
    override val image =
        "https://" + dto.cover!!.uri!!.replace("%%", "700x700")
}