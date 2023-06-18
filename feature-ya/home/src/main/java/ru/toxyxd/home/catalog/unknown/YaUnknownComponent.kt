package ru.toxyxd.home.catalog.unknown

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.home.component.unknown.UnknownComponent

class YaUnknownComponent(
    val id: String,
    val type: String,
    val title: String?,
    componentContext: ComponentContext
) : UnknownComponent, ComponentContext by componentContext {
    override val message = "Unknown block: id = ${id}, type = ${type}, title = ${title}"
    override val itemId = id
}