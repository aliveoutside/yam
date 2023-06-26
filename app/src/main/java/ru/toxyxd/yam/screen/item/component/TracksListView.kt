package ru.toxyxd.yam.screen.item.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.toxyxd.item.component.AlbumTrackComponent
import ru.toxyxd.item.component.PlaylistTrackComponent
import ru.toxyxd.item.component.TrackListComponent

@Composable
fun TrackListView(component: TrackListComponent) {
    val tracks = component.tracks.subscribeAsState()

    LazyColumn {
        items(tracks.value.size) {
            when (val track = tracks.value[it]) {
                is AlbumTrackComponent -> AlbumTrackView(track)
                is PlaylistTrackComponent -> PlaylistTrackView(track)
            }
        }
    }
}