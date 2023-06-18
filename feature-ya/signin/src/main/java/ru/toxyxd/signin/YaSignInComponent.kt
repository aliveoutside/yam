package ru.toxyxd.signin

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import org.koin.core.component.KoinComponent
import ru.toxyxd.yaapi.YaApi

internal class YaSignInComponent(
    componentContext: ComponentContext,
    private val yaApi: YaApi,
    private val onCodeRequested: () -> Unit,
) : SignInComponent, ComponentContext by componentContext, KoinComponent {
    override val login = MutableValue("")
    override fun onLoginChanged(login: String) {
        this.login.value = login
    }

    override fun provideAuthUrl(): String {
        return yaApi.authentication.generateAuthUrl(login.value)
    }

    override fun onCodeRequest() {
        onCodeRequested()
    }
}
