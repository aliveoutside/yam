package ru.toxyxd.yam.screen.nowplaying.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import ru.toxyxd.yam.ui.theme.surfaceColorAtAlpha

@Composable
fun PlayerCover(
    artworkUri: String?,
    requestImageSize: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val request = remember(artworkUri) {
        ImageRequest.Builder(context)
            .data(artworkUri)
            .size(requestImageSize)
            .crossfade(true)
            .allowHardware(false)
            .build()
    }
    val painter = rememberAsyncImagePainter(
        model = request
    )
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceColorAtAlpha(0.1f))
    ) {
        if (painter.state is AsyncImagePainter.State.Success) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }
        if (painter.state is AsyncImagePainter.State.Error) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
                tint = LocalContentColor.current
            )
        }
    }
}