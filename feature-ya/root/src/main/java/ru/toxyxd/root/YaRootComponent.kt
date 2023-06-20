package ru.toxyxd.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.toxyxd.signin.YaSignInRootComponent
import ru.toxyxd.yaapi.YaApi

class YaRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext, KoinComponent {
    private val yaApi by inject<YaApi>()
    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialStack = ::initialStack,
        childFactory = ::child
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun initialStack(): List<Config> =
        if (yaApi.isAuthorized) listOf(Config.ApplicationContent) else listOf(Config.SignIn)

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child {
        return when (config) {
            Config.SignIn -> {
                RootComponent.Child.SignIn(
                    YaSignInRootComponent(
                        componentContext = componentContext,
                        yaApi = yaApi,
                        onSuccess = {
                            navigation.replaceCurrent(Config.ApplicationContent)
                        }
                    )
                )
            }

            Config.ApplicationContent -> {
                RootComponent.Child.ApplicationContent(
                    YaContentComponent(
                        yaApi,
                        componentContext = componentContext
                    )
                )
            }
        }
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object SignIn : Config

        @Parcelize
        object ApplicationContent : Config
    }
}