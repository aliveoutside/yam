package ru.toxyxd.home

import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.yaapi.dto.catalog.CatalogBlockDto

fun CatalogBlockDto.Type.asSliderType() = when (this) {
    CatalogBlockDto.Type.PersonalPlaylists,
    CatalogBlockDto.Type.PlayContexts-> SliderComponent.SliderType.Horizontal
    CatalogBlockDto.Type.Promotions -> SliderComponent.SliderType.FullSize
    else -> SliderComponent.SliderType.Horizontal
}