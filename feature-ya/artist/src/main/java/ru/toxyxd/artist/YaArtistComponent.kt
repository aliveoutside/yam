package ru.toxyxd.artist

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.artist.component.ToolbarComponent
import ru.toxyxd.artist.component.toolbar.YaToolbarComponent
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto

class YaArtistComponent(
    artistInfo: ArtistBriefInfoDto,
    onGoBack: () -> Unit,
    componentContext: ComponentContext
) : ArtistComponent, ComponentContext by componentContext {
    override val toolbarComponent: ToolbarComponent = YaToolbarComponent(artistInfo, { onGoBack() }, componentContext)
}