package ru.toxyxd.yam.screen.item

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.SnapConfig
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import ru.toxyxd.item.ItemRootComponent
import ru.toxyxd.item.component.AlbumTrackComponent
import ru.toxyxd.item.component.PlaylistTrackComponent
import ru.toxyxd.yam.screen.item.component.AlbumTrackView
import ru.toxyxd.yam.screen.item.component.PlaylistTrackView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemRootView(root: ItemRootComponent) {
    val state = root.state.subscribeAsState()
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    val isTitleVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }
    val titleToolbarAlpha by animateFloatAsState(
        targetValue = if (!isTitleVisible) 1f else 0f, label = ""
    )

    val coverUrl = root.toolbarComponent.coverUrl.subscribeAsState()
    val title = root.toolbarComponent.title.subscribeAsState()
    val subtitle = root.toolbarComponent.subtitle.subscribeAsState()

    CollapsingToolbarScaffold(modifier = Modifier,
        state = scaffoldState,
        snapConfig = SnapConfig(),
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = title.value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 26.sp,
                    modifier = Modifier
                        .alpha(titleToolbarAlpha)
                        .padding(vertical = 16.dp)
                )
            }, navigationIcon = {
                IconButton(onClick = root.toolbarComponent::onBackClicked) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                    )
                }
            })
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = scaffoldState.toolbarState.progress
                }) {
                Card(
                    modifier = Modifier
                        .size(250.dp)
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
            }
        }) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            when (state.value) {
                is ItemRootComponent.State.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is ItemRootComponent.State.Loaded -> {
                    val tracks = root.tracklistComponent.tracks.subscribeAsState()

                    LazyColumn(
                        state = lazyListState, verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                text = title.value,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                            )
                        }
                        if (subtitle.value.isNotEmpty()) {
                            item {
                                Text(
                                    text = subtitle.value, fontWeight = FontWeight.Light
                                )
                            }
                        }

                        items(tracks.value.size) {
                            when (val track = tracks.value[it]) {
                                is AlbumTrackComponent -> AlbumTrackView(track)
                                is PlaylistTrackComponent -> PlaylistTrackView(track)
                            }
                        }
                    }
                }
            }
        }
    }
}