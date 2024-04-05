package ru.toxyxd.artist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.toxyxd.artist.component.AlbumsComponent
import ru.toxyxd.artist.component.SimilarArtistsComponent
import ru.toxyxd.artist.component.ToolbarComponent
import ru.toxyxd.artist.component.TopTracksComponent
import ru.toxyxd.artist.component.YaAlbumsComponent
import ru.toxyxd.artist.component.YaAlsoAlbumsComponent
import ru.toxyxd.artist.component.YaSimilarArtistsComponent
import ru.toxyxd.artist.component.YaToolbarComponent
import ru.toxyxd.artist.component.YaTopTracksComponent
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaArtistComponent(
    artistInfo: ArtistBriefInfoDto,
    onArtistClicked: (String) -> Unit,
    onItemClicked: (YaApiEntrypoint) -> Unit,
    onGoBack: () -> Unit,
    onPlayerEvent: (PlayerComponent.Event) -> Unit,
    componentContext: ComponentContext
) : ArtistComponent, ComponentContext by componentContext {
    override val toolbarComponent: ToolbarComponent = YaToolbarComponent(artistInfo, onGoBack, childContext("toolbar"))
    override val topTracksComponent: TopTracksComponent = YaTopTracksComponent(artistInfo, onPlayerEvent, childContext("topTracks"))
    override val albumsComponent: AlbumsComponent = YaAlbumsComponent(artistInfo, onItemClicked, childContext("albums"))
    override val alsoAlbumsComponent: AlbumsComponent = YaAlsoAlbumsComponent(artistInfo, onItemClicked, childContext("alsoAlbums"))
    override val similarArtistsComponent: SimilarArtistsComponent = YaSimilarArtistsComponent(artistInfo, onArtistClicked, childContext("similarArtists"))
}