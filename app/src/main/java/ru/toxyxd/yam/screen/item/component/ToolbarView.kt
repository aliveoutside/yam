package ru.toxyxd.yam.screen.item.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import ru.toxyxd.item.component.ToolbarComponent

@Composable
fun ToolbarView(component: ToolbarComponent, toolbarState: CollapsingToolbarScaffoldState) {
    val title = component.title.subscribeAsState()

    Row {
        Text(text = title.value)
    }
}