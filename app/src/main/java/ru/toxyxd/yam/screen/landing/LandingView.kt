package ru.toxyxd.yam.screen.landing

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.toxyxd.home.HomeComponent
import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.home.component.unknown.UnknownComponent
import ru.toxyxd.yam.screen.landing.component.slider.SliderView

@Composable
fun LandingView(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val components by component.children.subscribeAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(components) { component ->
            when (component) {
                is SliderComponent -> SliderView(component = component)
                is UnknownComponent -> {
                    Text(text = component.message)
                }
            }
        }
    }
}