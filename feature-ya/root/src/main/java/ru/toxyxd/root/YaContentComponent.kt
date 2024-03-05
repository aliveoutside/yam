package ru.toxyxd.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.toxyxd.home.YaHomeRootComponent
import ru.toxyxd.item.YaItemRootComponent
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaContentComponent(
    private val yaApi: YaApi,
    componentContext: ComponentContext
) : ContentComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    private val navigationMap = mapOf(
        ContentComponent.NavigationItem.Home to Config.Home,
    )

    override val navigationItems = ContentComponent.NavigationItem.entries
    override val currentNavigationItem = MutableValue(ContentComponent.NavigationItem.Home)
    override val childStack: Value<ChildStack<*, ContentComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = ::initialStack,
        handleBackButton = true,
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
        is Config.Item -> ContentComponent.Child.Item(item(config, componentContext))
    }

    private fun home(componentContext: ComponentContext) =
        YaHomeRootComponent(
            yaApi,
            onItemClicked = ::onItemClicked,
            componentContext = componentContext
        )

    private fun item(config: Config.Item, componentContext: ComponentContext) =
        YaItemRootComponent(
            yaApi,
            config.entrypoint,
            onGoBack = ::onGoBack,
            componentContext = componentContext
        )

    private fun onItemClicked(entrypoint: YaApiEntrypoint) {
        navigation.push(Config.Item(entrypoint))
    }

    private fun onGoBack() {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data class Item(val entrypoint: YaApiEntrypoint) : Config
    }
}


