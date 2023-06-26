package ru.toxyxd.yam.screen.item.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.toxyxd.item.component.AlbumTrackComponent

@Composable
fun AlbumTrackView(component: AlbumTrackComponent) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${component.index + 1}.",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .padding(end = 12.dp)
                .align(Alignment.CenterVertically)
        )
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