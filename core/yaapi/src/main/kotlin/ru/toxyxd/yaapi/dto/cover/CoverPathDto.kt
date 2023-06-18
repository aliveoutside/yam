package ru.toxyxd.yaapi.dto.cover

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoverPathDto(
    @SerialName("copyrightCline")
    val copyrightCline: String? = null,

    @SerialName("copyrightName")
    val copyrightName: String? = null,

    @SerialName("uri")
    val uri: String? = null
)


