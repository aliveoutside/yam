package ru.toxyxd.signin

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import kotlinx.parcelize.Parcelize
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.account.YaAccount

class YaSignInRootComponent(
    componentContext: ComponentContext,
    private val yaApi: YaApi,
    private val onSuccess: () -> Unit
) : SignInRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val childStack = childStack(
        source = navigation,
        initialStack = ::initialStack,
        childFactory = ::child
    )

    private fun initialStack(): List<Config> {
        return listOf(Config.SignIn)
    }

    private fun onSuccess(account: YaAccount) {
        yaApi.saveNewAccount(account)
        onSuccess()
    }

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): SignInRootComponent.Child {
        return when (config) {
            Config.SignIn -> {
                SignInRootComponent.Child.SignIn(
                    YaSignInComponent(
                        componentContext = componentContext,
                        yaApi = yaApi,
                        onSuccess = ::onSuccess
                    )
                )
            }
        }
    }

    sealed class Config : Parcelable {
        @Parcelize
        object SignIn : Config()
    }
}