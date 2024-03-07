package ru.toxyxd.yam.screen.signin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import ru.toxyxd.signin.SignInComponent
import ru.toxyxd.signin.SignInRootComponent

@Composable
fun SignInView(root: SignInRootComponent) {
    Children(stack = root.childStack) {
        when (val child = it.instance) {
            is SignInRootComponent.Child.SignIn -> {
                SignInContent(child.component)
            }
        }
    }
}

@Composable
private fun SignInContent(component: SignInComponent) {
    fun handleResult(result: YandexAuthResult) {
        when (result) {
            is YandexAuthResult.Success -> {
                component.onTokenReceived(result.token.value, result.token.expiresIn)
            }
            is YandexAuthResult.Failure -> {
                throw IllegalStateException("YandexAuthResult.Error: ${result.exception}")
            }

            YandexAuthResult.Cancelled -> { }
        }
    }

    val context = LocalContext.current
    val sdk = YandexAuthSdk.create(YandexAuthOptions(context))
    val launcher =
        rememberLauncherForActivityResult(sdk.contract) { result -> handleResult(result) }

    Scaffold { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = {
                    launcher.launch(YandexAuthLoginOptions())
                }
            ) {
                Text("Sign in")
            }
        }
    }
}
