package ru.toxyxd.item.component

import com.arkivanov.decompose.value.Value

interface ToolbarComponent {
    val title: Value<String>
    val subtitle: Value<String>
    val coverUrl: Value<String>
    fun onBackClicked()
}