package ru.toxyxd.root

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.toxyxd.artist.ArtistRootComponent
import ru.toxyxd.home.HomeRootComponent
import ru.toxyxd.item.ItemRootComponent
import ru.toxyxd.player.PlayerComponent

interface ContentComponent {
    val navigationItems: List<NavigationItem>
    val currentNavigationItem: Value<NavigationItem>

    val childStack: Value<ChildStack<*, Child>>

    val player: Value<ChildSlot<*, PlayerComponent>>

    fun selectNavigationItem(item: NavigationItem)

    sealed class Child {
        class Artist(val component: ArtistRootComponent) : Child()
        class Home(val component: HomeRootComponent) : Child()
        class Item(val component: ItemRootComponent) : Child()
    }

    enum class NavigationItem {
        Home,
    }
}