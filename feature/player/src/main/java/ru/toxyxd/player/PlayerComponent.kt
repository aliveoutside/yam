package ru.toxyxd.player

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.toxyxd.item.component.TrackComponent

interface PlayerComponent {
    val nowPlaying: StateFlow<TrackComponent?>
    val isPlaying: MutableStateFlow<Boolean>
    val progressFlow: Flow<Long>
    val duration: Long

    fun play()
    fun play(mediaItems: List<TrackComponent>, position: Int = 0, playedFromId: String = "")
    fun pause()
    fun next()
    fun previous()
    fun seekTo(position: Long)
    fun onInputEvent(event: Event)

    sealed class Event {
        data class Play(val mediaItems: List<TrackComponent>, val position: Int, val playedFromId: String) : Event()
        data object PlayPause : Event()
        data object Next : Event()
        data object Previous : Event()
        data class SeekTo(val position: Long) : Event()
    }
}