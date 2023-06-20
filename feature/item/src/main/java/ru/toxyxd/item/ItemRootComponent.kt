package ru.toxyxd.item

import com.arkivanov.decompose.value.Value
import ru.toxyxd.item.component.ToolbarComponent
import ru.toxyxd.item.component.TrackListComponent

interface ItemRootComponent {
    val state: Value<State>

    val toolbarComponent: ToolbarComponent
    val tracklistComponent: TrackListComponent

    sealed class State {
        object Loaded: State()
        object Loading: State()
    }
}