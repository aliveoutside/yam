package ru.toxyxd.yam.screen.artist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.toxyxd.artist.ArtistComponent
import ru.toxyxd.yam.screen.artist.component.AlbumsView
import ru.toxyxd.yam.screen.artist.component.ToolbarScaffold
import ru.toxyxd.yam.screen.artist.component.TopTracksView

@Composable
fun ArtistView(component: ArtistComponent) {
    ToolbarScaffold(component = component.toolbarComponent) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopTracksView(component = component.topTracksComponent)
            AlbumsView(component = component.albumsComponent)
        }
    }
}