package ru.toxyxd.signin

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.signin.controller.SignInController
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.account.YaAccount

internal class YaSignInComponent(
    componentContext: ComponentContext,
    onSuccess: (YaAccount) -> Unit,
    yaApi: YaApi,
) : SignInComponent, ComponentContext by componentContext,
    CoroutineScope by componentContext.componentCoroutineScope(), KoinComponent {
    private val controller = SignInController(yaApi, onSuccess = onSuccess) {
        Log.e("SignInComponent", it)
    }

    override fun onTokenReceived(token: String, expiresIn: Long) {
        launch {
            controller.signIn(token, expiresIn)
        }
    }
}
