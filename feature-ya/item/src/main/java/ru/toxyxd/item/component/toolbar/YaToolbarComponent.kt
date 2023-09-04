package ru.toxyxd.item.component.toolbar

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import ru.toxyxd.item.component.ToolbarComponent

class YaToolbarComponent(
    override val title: Value<String>,
    override val subtitle: Value<String>,
    override val coverUrl: Value<String>,
    val onGoBack: () -> Unit,
    componentContext: ComponentContext,
) : ToolbarComponent, ComponentContext by componentContext {

    override fun onBackClicked() {
        onGoBack()
    }
}