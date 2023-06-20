package ru.toxyxd.yam.screen.item.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.toxyxd.item.component.TrackListComponent

@Composable
fun TrackListView(component: TrackListComponent) {
    val tracks = component.tracks.subscribeAsState()
    Column {
        tracks.value.forEach {
            PlaylistTrackView(component = it)
        }
    }
}