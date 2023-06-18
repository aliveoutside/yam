package ru.toxyxd.yaapi.dto.cover

import kotlinx.serialization.Serializable

@Serializable
data class CoverInfoDto(
    val custom: Boolean? = null,
    val itemsUri: List<String>? = null,
    val type: String? = null,
    val uri: String? = null,
)
