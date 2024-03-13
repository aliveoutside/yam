package ru.toxyxd.yam.screen.artist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.toxyxd.artist.ArtistComponent
import ru.toxyxd.yam.screen.artist.component.ToolbarScaffold

@Composable
fun ArtistView(component: ArtistComponent) {
    ToolbarScaffold(component = component.toolbarComponent) {
        Text(text = "ArtistRootView")
    }
}