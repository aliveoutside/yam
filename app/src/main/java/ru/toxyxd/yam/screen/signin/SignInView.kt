package ru.toxyxd.yam.screen.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.toxyxd.signin.SignInComponent
import ru.toxyxd.signin.SignInRootComponent

@Composable
fun SignInView(root: SignInRootComponent) {
    Children(stack = root.childStack) {
        when (val child = it.instance) {
            is SignInRootComponent.Child.SignIn -> {
                SignInContent(child.component)
            }

            is SignInRootComponent.Child.EnterCode -> {
                EnterCodeView(child.component)
            }
        }
    }
}

@Composable
private fun SignInContent(component: SignInComponent) {
    val login by component.login.subscribeAsState()
    val uriHandler = LocalUriHandler.current
    Scaffold { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = login,
                onValueChange = { component.onLoginChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Login") }
            )
            Button(
                onClick = {
                    uriHandler.openUri(component.provideAuthUrl())
                    component.onCodeRequest()
                }
            ) {
                Text("Sign in")
            }
        }
    }
}
