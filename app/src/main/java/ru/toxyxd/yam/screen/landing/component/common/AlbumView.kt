package ru.toxyxd.yam.screen.landing.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.toxyxd.home.component.common.AlbumComponent
import ru.toxyxd.yam.ext.bouncingClickable

@Composable
fun AlbumView(component: AlbumComponent) {
    Column(
        modifier = Modifier.width(180.dp).bouncingClickable { component.onClick() }, verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            model = component.cover,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = component.title,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = component.artist,
            style = TextStyle(fontSize = 14.sp),
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis
        )
        if (component.type != null) {
            Text(
                text = component.type!!,
                style = TextStyle(fontSize = 14.sp),
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}