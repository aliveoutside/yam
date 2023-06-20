package ru.toxyxd.yam.screen.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.toxyxd.item.ItemRootComponent
import ru.toxyxd.yam.screen.item.component.TrackListView

@Composable
fun ItemRootView(root: ItemRootComponent) {
    val state = root.state.subscribeAsState()

    when (state.value) {
        is ItemRootComponent.State.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is ItemRootComponent.State.Loaded -> {
            TrackListView(component = root.tracklistComponent)
        }
    }
}