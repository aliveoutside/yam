package ru.toxyxd.artist

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface ArtistRootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Loaded(val component: ArtistComponent) : Child()
        data object Loading : Child()
    }
}