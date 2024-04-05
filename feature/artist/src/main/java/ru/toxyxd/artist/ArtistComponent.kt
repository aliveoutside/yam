package ru.toxyxd.artist

import ru.toxyxd.artist.component.AlbumsComponent
import ru.toxyxd.artist.component.ToolbarComponent
import ru.toxyxd.artist.component.TopTracksComponent

interface ArtistComponent {
    val toolbarComponent: ToolbarComponent
    val topTracksComponent: TopTracksComponent
    val albumsComponent: AlbumsComponent
    val alsoAlbumsComponent: AlbumsComponent
}