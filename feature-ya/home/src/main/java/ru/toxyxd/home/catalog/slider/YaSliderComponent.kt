package ru.toxyxd.home.catalog.slider

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.HasId
import ru.toxyxd.common.HasIdComponent
import ru.toxyxd.home.component.slider.SliderComponent

class YaSliderComponent(
    override val itemId: String,
    override val items: List<HasId>,
    override val sliderType: SliderComponent.SliderType,
    override val headerTitle: String,
    componentContext: ComponentContext,
) : SliderComponent, HasIdComponent, ComponentContext by componentContext