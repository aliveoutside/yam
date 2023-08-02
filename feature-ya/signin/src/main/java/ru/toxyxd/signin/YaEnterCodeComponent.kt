package ru.toxyxd.signin

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.signin.controller.SignInController
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.account.YaAccount

class YaEnterCodeComponent(
    componentContext: ComponentContext,
    yaApi: YaApi,
    onSuccess: (YaAccount) -> Unit,
) : EnterCodeComponent, ComponentContext by componentContext,
    CoroutineScope by componentContext.componentCoroutineScope(), KoinComponent {
    private val signInController = SignInController(
        yaApi = yaApi, onSuccess = onSuccess, onError = {
            Log.e("YaEnterCodeComponent", "Error: $it")
        }
    )

    override val code = MutableValue("")

    override fun onCodeChanged(code: String) {
        this.code.value = code
    }

    override fun onSignInClicked() {
        launch {
            signInController.signIn(code = code.value)
        }
    }
}