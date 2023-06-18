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
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.toxyxd.signin.EnterCodeComponent

@Composable
fun EnterCodeView(component: EnterCodeComponent) {
    val code by component.code.subscribeAsState()

    Scaffold { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = code,
                onValueChange = { component.onCodeChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Code") }
            )
            Button(
                onClick = {
                    component.onSignInClicked()
                }
            ) {
                Text("Sign in")
            }
        }
    }
}