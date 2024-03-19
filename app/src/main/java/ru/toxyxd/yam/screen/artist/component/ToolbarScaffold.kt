package ru.toxyxd.yam.screen.artist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.SnapConfig
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import ru.toxyxd.artist.component.ToolbarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarScaffold(component: ToolbarComponent, content: @Composable () -> Unit) {
    val title = component.title.subscribeAsState()
    val coverUrl = component.coverUrl.subscribeAsState()

    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val progress = scaffoldState.toolbarState.progress
    CollapsingToolbarScaffold(modifier = Modifier,
        state = scaffoldState,
        snapConfig = SnapConfig(),
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            CenterAlignedTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = component::onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = scaffoldState.toolbarState.progress
                    }
            ) {
                if (coverUrl.value.isNotEmpty()) {
                    AsyncImage(
                        model = coverUrl.value,
                        contentDescription = null,
                        alignment = Alignment.TopStart,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    0F to Color.Transparent,
                                    0.6f to Color.Transparent,
                                    1F to Color(0xBB000000),
                                ),
                            )
                    )
                }
            }
            Text(
                text = title.value,
                fontSize = (30 + 6 * progress).sp,
                fontWeight = FontWeight((FontWeight.Normal.weight + (FontWeight.Bold.weight - FontWeight.Normal.weight) * progress).toInt()),
                modifier = Modifier
                    .padding(16.dp)
                    .road(
                        whenCollapsed = Alignment.TopCenter,
                        whenExpanded = Alignment.BottomStart
                    )
            )
        }
    ) {
        content()
    }
}