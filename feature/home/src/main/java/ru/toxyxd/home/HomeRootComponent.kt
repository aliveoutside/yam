package ru.toxyxd.home

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface HomeRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(
            val component: HomeComponent
        ) : Child()
    }
}