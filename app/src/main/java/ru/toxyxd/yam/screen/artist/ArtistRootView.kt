package ru.toxyxd.yam.screen.artist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.toxyxd.artist.ArtistRootComponent

@Composable
fun ArtistRootView(root: ArtistRootComponent) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Children(stack = root.stack, modifier = Modifier.fillMaxSize()) {
            when (val child = it.instance) {
                is ArtistRootComponent.Child.Loaded -> ArtistView(child.component)
                is ArtistRootComponent.Child.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}