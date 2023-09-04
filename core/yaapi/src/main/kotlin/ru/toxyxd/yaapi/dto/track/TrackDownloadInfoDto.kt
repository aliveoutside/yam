package ru.toxyxd.yaapi.dto.track

import kotlinx.serialization.Serializable

@Serializable
data class TrackDownloadInfoDto(
    var downloadInfoUrl: String? = null,
)
