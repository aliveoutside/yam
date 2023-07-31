package ru.toxyxd.yaapi.dto.landing

import kotlinx.serialization.Serializable

@Serializable
data class LandingRootDto(
    val blocks: List<LandingBlockDto>
)
