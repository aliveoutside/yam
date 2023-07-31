package ru.toxyxd.yam

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import ru.toxyxd.root.ContentComponent
import ru.toxyxd.root.RootComponent
import ru.toxyxd.yam.screen.item.ItemRootView
import ru.toxyxd.yam.screen.landing.LandingRootView
import ru.toxyxd.yam.screen.signin.SignInView

@Composable
fun ContentView(
    rootComponent: RootComponent
) {
    Children(stack = rootComponent.childStack) {
        when (val child = it.instance) {
            is RootComponent.Child.SignIn -> SignInView(root = child.component)
            is RootComponent.Child.ApplicationContent -> Content(child.component)
        }
    }
}

@Composable
private fun Content(
    component: ContentComponent
) {
    Scaffold { paddings ->
        Children(
            stack = component.childStack,
            modifier = Modifier.padding(paddings),
            animation = stackAnimation(fade() + scale())
        ) {
            when (val child = it.instance) {
                is ContentComponent.Child.Home -> LandingRootView(root = child.component)
                is ContentComponent.Child.Item -> ItemRootView(root = child.component)
            }
        }
    }
}
