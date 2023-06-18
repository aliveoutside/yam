package ru.toxyxd.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.toxyxd.home.HomeRootComponent

interface ContentComponent {
    val navigationItems: List<NavigationItem>
    val currentNavigationItem: Value<NavigationItem>

    val childStack: Value<ChildStack<*, Child>>

    fun selectNavigationItem(item: NavigationItem)

    sealed class Child {
        class Home(val component: HomeRootComponent) : Child()
    }

    enum class NavigationItem {
        Home,
    }
}