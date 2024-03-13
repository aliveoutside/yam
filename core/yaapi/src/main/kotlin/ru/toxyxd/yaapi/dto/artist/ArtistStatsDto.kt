package ru.toxyxd.yaapi.dto.artist

import kotlinx.serialization.Serializable

@Serializable
data class ArtistStatsDto(
    val lastMonthListeners: Int
)
