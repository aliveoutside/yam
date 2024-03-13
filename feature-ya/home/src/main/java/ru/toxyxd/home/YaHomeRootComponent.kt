package ru.toxyxd.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaHomeRootComponent(
    private val yaApi: YaApi,
    private val onArtistClicked: (String) -> Unit,
    private val onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : HomeRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, HomeRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = ::initialStack,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun initialStack() = listOf(Config.Landing)

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): HomeRootComponent.Child {
        return when (config) {
            Config.Landing -> HomeRootComponent.Child.Home(
                YaHomeComponent(yaApi, onArtistClicked, onItemClicked, componentContext)
            )
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Landing : Config()
    }
}