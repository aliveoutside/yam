package ru.toxyxd.yam.screen.landing.component.slider

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import ru.toxyxd.home.component.common.AlbumComponent
import ru.toxyxd.home.component.common.PlaylistComponent
import ru.toxyxd.home.component.promotion.PromotionComponent
import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.home.component.unknown.UnknownComponent
import ru.toxyxd.yam.screen.landing.component.common.AlbumView
import ru.toxyxd.yam.screen.landing.component.common.PlaylistView
import ru.toxyxd.yam.screen.landing.component.header.HeaderView
import ru.toxyxd.yam.screen.landing.component.promotion.PromotionView

@Composable
fun SliderView(component: SliderComponent) {
    HeaderView(text = component.headerTitle)
    when (component.sliderType) {
        SliderComponent.SliderType.Horizontal -> HorizontalSliderView(component)
        SliderComponent.SliderType.FullSize -> FullSizeSliderView(component)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullSizeSliderView(component: SliderComponent) {
    val state = rememberLazyListState()
    val items = component.items.filterIsInstance<PromotionComponent>()

    LazyRow(
        state = state,
        modifier = Modifier.fillMaxSize(),
        flingBehavior = rememberSnapFlingBehavior(state),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items) { index, childComponent ->
            Column(
                modifier = Modifier
                    .graphicsLayer {
                        val lazyListOffset =
                            (state.firstVisibleItemIndex - index) + state.firstVisibleItemScrollOffset
                                .toFloat()
                                .div(1000)

                        lerp(
                            start = 0.9f,
                            stop = 1f,
                            fraction = 1f - lazyListOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - lazyListOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillParentMaxWidth()
            ) {
                PromotionView(component = childComponent)
            }
        }
    }
}

@Composable
fun HorizontalSliderView(component: SliderComponent) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(component.items) { item ->
            when (item) {
                is AlbumComponent -> AlbumView(item)
                is PlaylistComponent -> PlaylistView(item)
                is UnknownComponent -> Text(text = item.message)
            }
        }
    }
}