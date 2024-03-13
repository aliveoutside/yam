package ru.toxyxd.yam.screen.landing.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.toxyxd.home.component.common.ArtistComponent
import ru.toxyxd.yam.ext.bouncingClickable

@Composable
internal fun ArtistView(
    component: ArtistComponent
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .bouncingClickable { component.onClick() },
        verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            model = component.image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
        )
        Text(
            text = component.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}