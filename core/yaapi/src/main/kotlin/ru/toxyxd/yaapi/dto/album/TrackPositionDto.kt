package ru.toxyxd.yaapi.dto.album

import kotlinx.serialization.Serializable

@Serializable
data class TrackPositionDto(
    val index: Int,
    val volume: Double
)
