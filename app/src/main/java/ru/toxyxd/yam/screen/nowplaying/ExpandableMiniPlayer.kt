package ru.toxyxd.yam.screen.nowplaying

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.MediaItem
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import org.koin.androidx.compose.koinViewModel
import ru.toxyxd.yam.ext.bouncingClickable
import ru.toxyxd.yam.ext.getSurfaceColor
import ru.toxyxd.yam.screen.nowplaying.component.BottomSheet
import ru.toxyxd.yam.screen.nowplaying.component.BottomSheetState
import ru.toxyxd.yam.screen.nowplaying.fullscreen.NowPlayingFullscreenComposition
import ru.toxyxd.yam.ui.theme.surfaceColorAtAlpha

@Composable
fun ExpandableMiniPlayer(
    bottomSheetState: BottomSheetState,
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel()
) {
    val mediaItem by viewModel.mediaItem.collectAsState()

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
    val isPlaying by viewModel.isPlaying.collectAsState()

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
            val listener = remember {
                object : ImageRequest.Listener {
                    override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                        super.onSuccess(request, result)
                        Palette
                            .from(result.drawable.toBitmap())
                            .clearFilters()
                            .generate {
                                val color = it?.getSurfaceColor() ?: defaultSurfaceColor.toArgb()
                                viewModel.dominantColor = Color(color)
                            }
                    }
                }
            }
            val context = LocalContext.current
            val request = remember(mediaItem) {
                ImageRequest.Builder(context)
                    .data(mediaItem?.mediaMetadata?.artworkUri)
                    .size(Size(96, 96))
                    .crossfade(true)
                    .listener(listener)
                    .allowHardware(false)
                    .build()
            }
            val painter = rememberAsyncImagePainter(
                model = request
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtAlpha(0.1f))
            ) {
                if (painter.state is AsyncImagePainter.State.Success) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp),
                        contentScale = ContentScale.FillBounds,
                    )
                } else {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .requiredSize(26.dp)
                            .align(Alignment.Center),
                        tint = LocalContentColor.current
                    )
                }
            }

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
