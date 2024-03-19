package ru.toxyxd.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import ru.toxyxd.artist.YaArtistRootComponent
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.home.YaHomeRootComponent
import ru.toxyxd.item.YaItemRootComponent
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.player.YaPlayerComponent
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaContentComponent(
    private val yaApi: YaApi,
    componentContext: ComponentContext
) : ContentComponent, ComponentContext by componentContext, CoroutineScope by componentContext.componentCoroutineScope() {
    private val navigation = StackNavigation<Config>()
    private val navigationMap = mapOf(
        ContentComponent.NavigationItem.Home to Config.Home,
    )

    private val playerNavigation = SlotNavigation<PlayerConfig>()
    private val _playerEventFlow = MutableSharedFlow<PlayerComponent.Event>()
    private val playerEventFlow = _playerEventFlow.asSharedFlow()

    override val player: Value<ChildSlot<*, PlayerComponent>> =
        childSlot(
            source = playerNavigation,
            initialConfiguration = { PlayerConfig.Player },
            serializer = PlayerConfig.serializer()
        ) { config, componentContext ->
            YaPlayerComponent(
                playerEventFlow,
                componentContext
            )
        }

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
        is Config.Artist -> ContentComponent.Child.Artist(artist(config, componentContext))
        Config.Home -> ContentComponent.Child.Home(home(componentContext))
        is Config.Item -> ContentComponent.Child.Item(item(config, componentContext))
    }

    private fun artist(config: Config.Artist, componentContext: ComponentContext) =
        YaArtistRootComponent(
            yaApi,
            config.artistId,
            onGoBack = ::onGoBack,
            onPlayerEvent = ::onPlayerEvent,
            componentContext = componentContext
        )

    private fun home(componentContext: ComponentContext) =
        YaHomeRootComponent(
            yaApi,
            onArtistClicked = ::onArtistClicked,
            onItemClicked = ::onItemClicked,
            componentContext = componentContext
        )

    private fun item(config: Config.Item, componentContext: ComponentContext) =
        YaItemRootComponent(
            yaApi,
            config.entrypoint,
            onGoBack = ::onGoBack,
            onPlayerEvent = ::onPlayerEvent,
            componentContext = componentContext
        )

    private fun onArtistClicked(artistId: String) {
        navigation.push(Config.Artist(artistId))
    }

    private fun onPlayerEvent(event: PlayerComponent.Event) {
        launch {
            _playerEventFlow.emit(event)
        }
    }

    private fun onItemClicked(entrypoint: YaApiEntrypoint) {
        navigation.push(Config.Item(entrypoint))
    }

    private fun onGoBack() {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data class Artist(val artistId: String) : Config

        @Serializable
        data object Home : Config

        @Serializable
        data class Item(val entrypoint: YaApiEntrypoint) : Config
    }

    @Serializable
    private sealed interface PlayerConfig {
        @Serializable
        data object Player : PlayerConfig
    }
}


