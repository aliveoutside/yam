package ru.toxyxd.yam.screen.item.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.toxyxd.item.component.ToolbarComponent

@Composable
fun ToolbarView(component: ToolbarComponent) {
    Row {
        Text(text = component.title)
    }
}