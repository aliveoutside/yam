package ru.toxyxd.signin

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface SignInRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class SignIn(val component: SignInComponent) : Child
    }
}