package ru.toxyxd.home.component.slider

import ru.toxyxd.common.HasHeaderTitle
import ru.toxyxd.common.HasId

interface SliderComponent : HasId, HasHeaderTitle {
    val items: List<HasId>
    val sliderType: SliderType

    enum class SliderType {
        Horizontal, FullSize
    }
}