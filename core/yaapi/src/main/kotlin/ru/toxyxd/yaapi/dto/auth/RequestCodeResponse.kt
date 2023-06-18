package ru.toxyxd.yaapi.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCodeResponse(
    @SerialName("device_code")
    val deviceCode: String,
    @SerialName("user_code")
    val userCode: String,
    @SerialName("verification_url")
    val verificationUrl: String,
    @SerialName("interval")
    val interval: Int,
    @SerialName("expires_in")
    val expiresIn: Int,
)
