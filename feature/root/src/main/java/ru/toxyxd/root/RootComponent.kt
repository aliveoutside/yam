package ru.toxyxd.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.toxyxd.signin.SignInRootComponent

interface RootComponent : BackHandlerOwner {
    val childStack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    sealed class Child {
        class SignIn(val component: SignInRootComponent) : Child()
        class ApplicationContent(val component: ContentComponent) : Child()
    }
}