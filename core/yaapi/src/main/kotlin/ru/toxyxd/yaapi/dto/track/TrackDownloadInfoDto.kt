package ru.toxyxd.yaapi.dto.track

import kotlinx.serialization.Serializable

@Serializable
data class TrackDownloadInfoDto(
    val bitrateInKbps: Int,
    var downloadInfoUrl: String,
)
