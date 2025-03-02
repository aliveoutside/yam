package ru.toxyxd.yam.screen.nowplaying

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.toxyxd.item.component.TrackComponent
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yam.ext.bouncingClickable
import ru.toxyxd.yam.screen.nowplaying.component.BottomSheet
import ru.toxyxd.yam.screen.nowplaying.component.BottomSheetState
import ru.toxyxd.yam.screen.nowplaying.fullscreen.NowPlayingControls
import ru.toxyxd.yam.screen.nowplaying.shared.PlayerCover
import ru.toxyxd.yam.ui.theme.surfaceColorAtAlpha


@Composable
fun ExpandableMiniPlayer(
    playerComponent: PlayerComponent,
    bottomSheetState: BottomSheetState,
    modifier: Modifier = Modifier,
) {
    val mediaItem by playerComponent.nowPlaying.collectAsStateWithLifecycle()

    BottomSheet(
        state = bottomSheetState,
        modifier = modifier,
        onDismiss = {},
        collapsedContent = {
            CollapsedMiniPlayer(mediaItem, playerComponent)
        },
    ) {
        NowPlayingControls(playerComponent, onCollapse = { bottomSheetState.collapseSoft() }, modifier = modifier)
    }
}

@Composable
private fun CollapsedMiniPlayer(track: TrackComponent?, playerComponent: PlayerComponent) {
    val isPlaying by playerComponent.isPlaying.collectAsStateWithLifecycle()

    Surface {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PlayerCover(
                artworkUri = track?.cover,
                requestImageSize = 200,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtAlpha(0.1f))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = track?.title ?: "",
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track?.artists?.joinToString(", ") ?: "",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    maxLines = 1,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .requiredSize(26.dp)
                    .bouncingClickable {
                        if (isPlaying) playerComponent.pause() else playerComponent.play()
                    },
                tint = LocalContentColor.current
            )
        }
    }
}
