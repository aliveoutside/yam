package ru.toxyxd.yam.screen.artist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.toxyxd.artist.component.SimilarArtistsComponent
import ru.toxyxd.yam.ext.ignoreHorizontalPadding
import ru.toxyxd.yam.screen.landing.component.slider.HorizontalSliderView

@Composable
fun SimilarArtistsView(component: SimilarArtistsComponent) {
    if (component.artistsSlider != null) {
        val slider by component.artistsSlider!!.subscribeAsState()

        Column {
            Text(
                text = slider.headerTitle,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                fontWeight = FontWeight.Bold
            )
            HorizontalSliderView(
                component = slider,
                modifier = Modifier.ignoreHorizontalPadding(12.dp)
            )
        }
    }
}