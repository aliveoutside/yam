package ru.toxyxd.home

import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.yaapi.dto.landing.LandingBlockDto

fun LandingBlockDto.Type.toSliderType() = when (this) {
    LandingBlockDto.Type.NewReleases,
    LandingBlockDto.Type.PersonalPlaylists,
    LandingBlockDto.Type.PlayContexts -> SliderComponent.SliderType.Horizontal

    LandingBlockDto.Type.Promotions -> SliderComponent.SliderType.FullSize
    else -> SliderComponent.SliderType.Horizontal
}