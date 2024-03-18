package ru.toxyxd.yam.screen.nowplaying.fullscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.toxyxd.player.PlayerComponent

@Composable
fun NowPlayingFullscreenComposition(
    playerComponent: PlayerComponent
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        NowPlayingControls(playerComponent = playerComponent, modifier = Modifier.align(Alignment.BottomStart))
    }
}