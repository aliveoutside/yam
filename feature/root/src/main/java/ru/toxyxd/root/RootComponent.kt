package ru.toxyxd.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.toxyxd.signin.SignInRootComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class SignIn(val component: SignInRootComponent) : Child()
        class ApplicationContent(val component: ContentComponent) : Child()
    }
}