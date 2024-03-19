package ru.toxyxd.artist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.toxyxd.artist.component.ToolbarComponent
import ru.toxyxd.artist.component.TopTracksComponent
import ru.toxyxd.artist.component.YaToolbarComponent
import ru.toxyxd.artist.component.YaTopTracksComponent
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto

class YaArtistComponent(
    artistInfo: ArtistBriefInfoDto,
    onGoBack: () -> Unit,
    onPlayerEvent: (PlayerComponent.Event) -> Unit,
    componentContext: ComponentContext
) : ArtistComponent, ComponentContext by componentContext {
    override val toolbarComponent: ToolbarComponent = YaToolbarComponent(artistInfo, onGoBack, childContext("toolbar"))
    override val topTracksComponent: TopTracksComponent = YaTopTracksComponent(artistInfo, onPlayerEvent, childContext("topTracks"))
}