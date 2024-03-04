package ru.toxyxd.yam.screen.nowplaying

import androidx.compose.animation.animateColorAsState
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
import androidx.media3.common.MediaItem
import org.koin.androidx.compose.koinViewModel
import ru.toxyxd.yam.ext.bouncingClickable
import ru.toxyxd.yam.screen.nowplaying.component.BottomSheet
import ru.toxyxd.yam.screen.nowplaying.component.BottomSheetState
import ru.toxyxd.yam.screen.nowplaying.fullscreen.NowPlayingFullscreenComposition
import ru.toxyxd.yam.screen.nowplaying.shared.PlayerCover
import ru.toxyxd.yam.ui.theme.surfaceColorAtAlpha

@Composable
fun ExpandableMiniPlayer(
    bottomSheetState: BottomSheetState,
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel()
) {
    val mediaItem by viewModel.mediaItem.collectAsStateWithLifecycle()

    BottomSheet(
        state = bottomSheetState,
        modifier = modifier,
        onDismiss = {},
        collapsedContent = {
            CollapsedMiniPlayer(mediaItem, viewModel)
        },
    ) {
        NowPlayingFullscreenComposition(viewModel)
    }
}

@Composable
private fun CollapsedMiniPlayer(mediaItem: MediaItem?, viewModel: NowPlayingViewModel) {
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()

    val defaultSurfaceColor = MaterialTheme.colorScheme.surfaceColorAtAlpha(0.8f)
    val surfaceColor = animateColorAsState(targetValue = viewModel.dominantColor)

    Surface(
        color = surfaceColor.value,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PlayerCover(
                artworkUri = mediaItem?.mediaMetadata?.artworkUri.toString(),
                requestImageSize = 96,
                onSuccess = { viewModel.extractColor(it, defaultSurfaceColor) },
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
                    text = mediaItem?.mediaMetadata?.title?.toString() ?: "",
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = mediaItem?.mediaMetadata?.artist?.toString() ?: "",
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
                    .bouncingClickable { viewModel.switchPlayPause() },
                tint = LocalContentColor.current
            )
        }
    }
}
