package ru.toxyxd.yam.screen.nowplaying.fullscreen

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import ru.toxyxd.yam.screen.nowplaying.NowPlayingViewModel

@Composable
fun NowPlayingControls(
    viewModel: NowPlayingViewModel,
    modifier: Modifier = Modifier
) {
    val track by viewModel.mediaItem.collectAsState()

    if (track != null) {
        Column(modifier) {
            Artwork(track = track!!)
            Spacer(Modifier.height(16.dp))
            Header(track = track!!)
            Spacer(Modifier.height(16.dp))
            Seekbar(viewModel = viewModel)
        }
    }
}

@Composable
private fun Artwork(
    track: MediaItem
) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = track.mediaMetadata.artworkUri,
            null,
            modifier = Modifier
                .size(128.dp)
        )
    }
}

@Composable
private fun Header(
    track: MediaItem
) {
    Text(
        text = track.mediaMetadata.title.toString(),
        modifier = Modifier
            .padding(horizontal = 14.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(2.dp))
    Text(
        text = track.mediaMetadata.artist.toString(),
        modifier = Modifier
            .padding(horizontal = 14.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 16.sp,
    )
}

@Composable
private fun Seekbar(
    viewModel: NowPlayingViewModel
) {
    val progress by viewModel.progress.collectAsState(0L)
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
    val totalTime = remember(viewModel.duration) {
        DateUtils.formatElapsedTime(viewModel.duration / 1000)
    }

    Slider(value = if (isSeekbarDragging) seekbarDraggingProgress else progress.toFloat(),
        onValueChange = {
            isSeekbarDragging = true
            seekbarDraggingProgress = it
        }, onValueChangeFinished = {
            viewModel.seekTo(seekbarDraggingProgress.toLong())
            isSeekbarDragging = false
        }, valueRange = 0f..viewModel.duration.toFloat()
    )
    Row(
        Modifier
            .padding(horizontal = 14.dp)
            .offset(y = (-6).dp)
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