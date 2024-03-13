package ru.toxyxd.artist.component

import com.arkivanov.decompose.value.Value

interface ToolbarComponent {
    val title: Value<String>
    val coverUrl: Value<String>

    fun onBackClicked()
}