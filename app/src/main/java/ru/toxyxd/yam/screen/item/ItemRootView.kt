package ru.toxyxd.yam.screen.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.SnapConfig
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import ru.toxyxd.item.ItemRootComponent
import ru.toxyxd.yam.screen.item.component.TrackListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemRootView(root: ItemRootComponent) {
    val state = root.state.subscribeAsState()
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    val coverUrl = root.toolbarComponent.coverUrl.subscribeAsState()
    val title = root.toolbarComponent.title.subscribeAsState()

    CollapsingToolbarScaffold(modifier = Modifier,
        state = scaffoldState,
        snapConfig = SnapConfig(),
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title.value,
                        modifier = Modifier.alpha(1f - scaffoldState.toolbarState.progress)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(20.dp)
                        .graphicsLayer {
                            alpha = scaffoldState.toolbarState.progress
                        }
                ) {
                    Card(
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Box {
                            if (coverUrl.value.isNotEmpty()) {
                                AsyncImage(
                                    model = coverUrl.value,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                    Text(
                        text = title.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }) {
        when (state.value) {
            is ItemRootComponent.State.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is ItemRootComponent.State.Loaded -> {
                TrackListView(component = root.tracklistComponent)
            }
        }
    }
}