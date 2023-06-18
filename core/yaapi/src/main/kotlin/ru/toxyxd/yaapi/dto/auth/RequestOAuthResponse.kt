package ru.toxyxd.yaapi.dto.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestOAuthResponse(
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("scope")
    val scope: String? = null
)