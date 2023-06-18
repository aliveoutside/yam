package ru.toxyxd.yaapi.dto.catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActionButtonDto(
    @SerialName("color")
    val color: String? = null,

    @SerialName("text")
    val text: String? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("viewBrowser")
    val viewBrowser: Boolean? = null
)

