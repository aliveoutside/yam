package ru.toxyxd.signin

import com.arkivanov.decompose.value.Value

interface SignInComponent {
    val login: Value<String>

    fun onLoginChanged(login: String)
    fun provideAuthUrl(): String
    fun onCodeRequest()
}