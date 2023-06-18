package ru.toxyxd.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.toxyxd.yaapi.YaApi

class YaHomeRootComponent(
    private val yaApi: YaApi,
    componentContext: ComponentContext
) : HomeRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, HomeRootComponent.Child>> = childStack(
        source = navigation,
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
                YaHomeComponent(yaApi, componentContext)
            )
        }
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Landing : Config()
    }
}