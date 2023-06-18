package ru.toxyxd.yaapi.account

import kotlinx.serialization.Serializable

@Serializable
data class YaAccount(
    val id: Int,
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String,
)