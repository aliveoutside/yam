package ru.toxyxd.artist.component

import com.arkivanov.decompose.value.Value
import ru.toxyxd.home.component.slider.SliderComponent

interface AlbumsComponent {
    val albumsSlider: Value<SliderComponent>?
}