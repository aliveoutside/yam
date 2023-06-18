package ru.toxyxd.yam.screen.landing

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.toxyxd.home.HomeRootComponent

@Composable
fun LandingRootView(
    root: HomeRootComponent
) {
    Children(stack = root.childStack) {
        when (val child = it.instance) {
            is HomeRootComponent.Child.Home -> LandingView(
                child.component
            )

            else -> error("Unknown child $child")
        }
    }
}