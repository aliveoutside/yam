package ru.toxyxd.signin

import com.arkivanov.decompose.value.Value

interface EnterCodeComponent {
    val code: Value<String>

    fun onCodeChanged(code: String)
    fun onSignInClicked()
}