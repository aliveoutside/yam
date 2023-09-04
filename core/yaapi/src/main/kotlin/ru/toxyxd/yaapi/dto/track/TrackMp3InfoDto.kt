package ru.toxyxd.yaapi.dto.track

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackMp3InfoDto(
    @SerialName("host")
    var host: String? = null,
    @SerialName("path")
    var path: String? = null,
    @SerialName("s")
    var s: String? = null,
    @SerialName("ts")
    var ts: String? = null
)