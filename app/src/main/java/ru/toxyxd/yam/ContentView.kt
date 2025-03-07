package ru.toxyxd.yam

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.toxyxd.root.ContentComponent
import ru.toxyxd.root.RootComponent
import ru.toxyxd.yam.screen.artist.ArtistRootView
import ru.toxyxd.yam.screen.item.ItemRootView
import ru.toxyxd.yam.screen.landing.LandingRootView
import ru.toxyxd.yam.screen.nowplaying.ExpandableMiniPlayer
import ru.toxyxd.yam.screen.nowplaying.component.collapsedAnchor
import ru.toxyxd.yam.screen.nowplaying.component.rememberBottomSheetState
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
    val miniPlayerHeight = 56.dp
    val playerSlot by component.player.subscribeAsState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        val playerBottomSheetState = rememberBottomSheetState(
            dismissedBound = 0.dp,
            collapsedBound = miniPlayerHeight,
            expandedBound = maxHeight,
            initialAnchor = collapsedAnchor
        )
        val bottomPadding by animateDpAsState(
            targetValue = if (!playerBottomSheetState.isDismissed) 56.dp else 0.dp
        )

        Scaffold { paddings ->
            Children(
                stack = component.childStack,
                modifier = Modifier
                    .padding(paddings)
                    .padding(bottom = bottomPadding),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val child = it.instance) {
                    is ContentComponent.Child.Artist -> ArtistRootView(root = child.component)
                    is ContentComponent.Child.Home -> LandingRootView(root = child.component)
                    is ContentComponent.Child.Item -> ItemRootView(root = child.component)
                }
            }

            playerSlot.child?.instance?.let { playerComponent ->
                ExpandableMiniPlayer(
                    playerComponent = playerComponent,
                    bottomSheetState = playerBottomSheetState,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }
    }
}
