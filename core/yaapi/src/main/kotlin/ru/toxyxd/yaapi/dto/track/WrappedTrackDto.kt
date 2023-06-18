package ru.toxyxd.yaapi.dto.track

import kotlinx.serialization.Serializable

@Serializable
data class WrappedTrackDto(
    val albumId: String,
    val id: String,
    val recent: Boolean,
    val track: TrackDto
)
