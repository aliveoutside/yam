package ru.toxyxd.yaapi.dto.track

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackFadeDto(
    @SerialName("inStart")
    val inStart: Double? = null,

    @SerialName("inStop")
    val inStop: Double? = null,

    @SerialName("outStart")
    val outStart: Double? = null,

    @SerialName("outStop")
    val outStop: Double? = null,
)

