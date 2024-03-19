package ru.toxyxd.artist.component

import com.arkivanov.decompose.value.Value
import ru.toxyxd.item.component.TopTrackComponent

interface TopTracksComponent {
    val tracks: Value<List<TopTrackComponent>>?

    fun play(index: Int)
}