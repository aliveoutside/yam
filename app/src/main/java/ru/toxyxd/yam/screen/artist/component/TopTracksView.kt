package ru.toxyxd.yam.screen.artist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.toxyxd.artist.component.TopTracksComponent
import ru.toxyxd.item.component.TopTrackComponent
import ru.toxyxd.yam.ui.theme.surfaceColorAtAlpha

@Composable
fun TopTracksView(component: TopTracksComponent, modifier: Modifier = Modifier) {
    if (component.tracks != null) {
        val tracks by component.tracks!!.subscribeAsState()

        Column(modifier = modifier) {
            Text(
                text = "Популярное",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                fontWeight = FontWeight.Bold
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                tracks.take(5).forEach { item ->
                    TopTrackView(item, onClick = { component.play(tracks.indexOf(item)) })
                }
            }
        }
    }
}

@Composable
fun TopTrackView(component: TopTrackComponent, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = "${component.index + 1}.",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .padding(end = 12.dp)
                .align(Alignment.CenterVertically)
        )
        SubcomposeAsyncImage(
            model = component.cover, contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceColorAtAlpha(0.1f)),
            contentScale = ContentScale.FillBounds,
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Success) {
                SubcomposeAsyncImageContent()
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
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = component.title,
                style = TextStyle(fontSize = 16.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}