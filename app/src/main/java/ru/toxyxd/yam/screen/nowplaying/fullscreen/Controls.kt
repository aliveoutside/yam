package ru.toxyxd.yam.screen.nowplaying.fullscreen

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yam.ext.bouncingClickable
import ru.toxyxd.yam.screen.nowplaying.shared.PlayerCover

@Composable
fun NowPlayingControls(
    playerComponent: PlayerComponent,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    val track by playerComponent.nowPlaying.collectAsStateWithLifecycle()

    if (track != null) {
        val title = track!!.title
        val artist = track!!.artists.joinToString(", ")
        val artworkUri = track!!.hugeCover
        val isPlaying by playerComponent.isPlaying.collectAsStateWithLifecycle()

        val progress by playerComponent.progressFlow.collectAsStateWithLifecycle(0L)

        NowPlayingControlsContent(
            title = title,
            artist = artist,
            artworkUri = artworkUri,
            isPlaying = isPlaying,
            progress = progress,
            duration = playerComponent.duration,
            onSeekTo = playerComponent::seekTo,
            onPlayPauseClick = {
                if (isPlaying) playerComponent.pause() else playerComponent.play()
            },
            onPreviousClick = playerComponent::previous,
            onNextClick = playerComponent::next,
            onAlbumClicked = {
                playerComponent.onAlbumClicked()
                onCollapse()
            },
            onArtistClicked = {
                playerComponent.onArtistClicked()
                onCollapse()
            },
            modifier = modifier,
        )
    }
}

@Composable
fun NowPlayingControlsContent(
    title: String,
    artist: String,
    artworkUri: String?,
    isPlaying: Boolean,
    progress: Long,
    duration: Long,
    onSeekTo: (Long) -> Unit,
    onPlayPauseClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onAlbumClicked: () -> Unit,
    onArtistClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    ) {
        Column {
            Spacer(Modifier.height(64.dp))
            Artwork(
                artworkUri = artworkUri,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Header(
                    title = title,
                    artist = artist,
                    onAlbumClicked = onAlbumClicked,
                    onArtistClicked = onArtistClicked
                )
                Spacer(Modifier.height(8.dp))
                TrackSeekbar(
                    progress = progress,
                    duration = duration,
                    onSeekTo = onSeekTo
                )
                Spacer(Modifier.height(16.dp))
                ControlButtons(
                    isPlaying = isPlaying,
                    onPlayPauseClicked = onPlayPauseClick,
                    onPreviousClicked = onPreviousClick,
                    onNextClicked = onNextClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Spacer(Modifier.padding(vertical = 64.dp))
        }
    }
}

@Composable
private fun Artwork(
    artworkUri: String?,
    modifier: Modifier,
) {
    val size = 384.dp
    PlayerCover(
        artworkUri = artworkUri,
        requestImageSize = with(LocalDensity.current) { size.toPx().toInt() },
        modifier = modifier
            .padding(horizontal = 16.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
private fun Header(
    title: String,
    artist: String,
    onAlbumClicked: () -> Unit,
    onArtistClicked: () -> Unit
) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.bouncingClickable { onAlbumClicked() }
    )
    Spacer(Modifier.height(2.dp))
    Text(
        text = artist,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 16.sp,
        modifier = Modifier.bouncingClickable { onArtistClicked() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackSeekbar(
    progress: Long,
    duration: Long,
    onSeekTo: (Long) -> Unit
) {
    var isSeekbarDragging by remember { mutableStateOf(false) }
    var seekbarDraggingProgress by remember { mutableFloatStateOf(0f) }

    val elapsedTime = remember(progress, isSeekbarDragging, seekbarDraggingProgress) {
        val ms = if (isSeekbarDragging) {
            seekbarDraggingProgress.toLong()
        } else {
            progress
        } / 1000

        DateUtils.formatElapsedTime(ms)
    }
    val totalTime = remember(duration) {
        DateUtils.formatElapsedTime(duration / 1000)
    }

    Slider(
        value = if (isSeekbarDragging) seekbarDraggingProgress else progress.toFloat(),
        onValueChange = {
            isSeekbarDragging = true
            seekbarDraggingProgress = it
            onSeekTo(it.toLong())
        },
        onValueChangeFinished = {
            isSeekbarDragging = false
        },
        valueRange = 0f..duration.toFloat(),
        thumb = {
            SliderDefaults.Thumb(
                interactionSource = remember { MutableInteractionSource() },
                thumbSize = DpSize(16.dp, 16.dp)
            )
        }
    )
    Row(
        Modifier.offset(y = (-6).dp)
    ) {
        Text(
            text = elapsedTime,
            modifier = Modifier.alpha(0.7f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = totalTime,
            fontSize = 12.sp,
            modifier = Modifier.alpha(0.7f)
        )
    }
}

@Composable
fun ControlButtons(
    isPlaying: Boolean,
    onPlayPauseClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    @Composable
    fun IconButton(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
        Box(
            modifier = modifier
                .size(40.0.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

    Row(modifier = modifier) {
        IconButton {
            Icon(
                Icons.Default.SkipPrevious,
                contentDescription = "Previous",
                modifier = Modifier
                    .requiredSize(26.dp)
                    .bouncingClickable { onPreviousClicked() },
                tint = LocalContentColor.current
            )
        }
        Spacer(modifier = Modifier.width(32.dp))
        IconButton {
            Icon(
                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "Play/Pause",
                modifier = Modifier
                    .requiredSize(26.dp)
                    .bouncingClickable { onPlayPauseClicked() },
                tint = LocalContentColor.current
            )
        }
        Spacer(modifier = Modifier.width(32.dp))
        IconButton {
            Icon(
                Icons.Default.SkipNext,
                contentDescription = "Next",
                modifier = Modifier
                    .requiredSize(26.dp)
                    .bouncingClickable { onNextClicked() },
                tint = LocalContentColor.current
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NowPlayingControlsPreview() {
    val title = "Title"
    val artist = "Artist"
    val progress = 1000L
    val duration = 10000L
    val isPlaying = true
    val onSeekTo = { _: Long -> }

    NowPlayingControlsContent(
        title = title,
        artist = artist,
        artworkUri = null,
        isPlaying = isPlaying,
        progress = progress,
        duration = duration,
        onSeekTo = onSeekTo,
        onPlayPauseClick = { },
        onPreviousClick = { },
        onNextClick = { },
        onAlbumClicked = { },
        onArtistClicked = { }
    )
}