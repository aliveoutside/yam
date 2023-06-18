package ru.toxyxd.yaapi.dto.artist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkDto(
    @SerialName("socialNetwork")
    val socialNetwork: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("href")
    val url: String? = null
)

