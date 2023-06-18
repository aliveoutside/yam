package ru.toxyxd.yaapi.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YaUser(
    val login: String,
    val id: Int,
    @SerialName("client_id")
    val clientId: String,
    val psuid: String
)
