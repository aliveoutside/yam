package ru.toxyxd.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.parcelize.Parcelize
import ru.toxyxd.home.YaHomeRootComponent
import ru.toxyxd.yaapi.YaApi

class YaContentComponent(
    private val yaApi: YaApi,
    componentContext: ComponentContext
) : ContentComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    private val navigationMap = mapOf(
        ContentComponent.NavigationItem.Home to Config.Home,
    )

    override val navigationItems = ContentComponent.NavigationItem.values().toList()
    override val currentNavigationItem = MutableValue(ContentComponent.NavigationItem.Home)
    override val childStack: Value<ChildStack<*, ContentComponent.Child>> = childStack(
        source = navigation,
        initialStack = ::initialStack,
        childFactory = ::child
    )

    override fun selectNavigationItem(item: ContentComponent.NavigationItem) {
        if (item != currentNavigationItem.value) {
            currentNavigationItem.value = item
            navigation.bringToFront(navigationMap.getValue(item))
        }
    }

    private fun initialStack() = listOf(Config.Home)

    private fun child(config: Config, componentContext: ComponentContext) = when (config) {
        Config.Home -> ContentComponent.Child.Home(home(componentContext))
    }

    private fun home(componentContext: ComponentContext) =
        YaHomeRootComponent(
            yaApi,
            componentContext = componentContext
        )

    private sealed interface Config : Parcelable {
        @Parcelize
        object Home : Config
    }
}


