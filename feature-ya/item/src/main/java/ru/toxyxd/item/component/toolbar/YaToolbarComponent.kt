package ru.toxyxd.item.component.toolbar

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.item.component.ToolbarComponent

class YaToolbarComponent(
    componentContext: ComponentContext
) : ToolbarComponent, ComponentContext by componentContext {
    override val title: String = "YaToolbarComponent"

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }
}