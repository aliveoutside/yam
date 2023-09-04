package ru.toxyxd.item.component

import com.arkivanov.decompose.value.Value

interface TrackListComponent {
    val tracks: Value<List<TrackComponent>>

    fun play(index: Int)
}