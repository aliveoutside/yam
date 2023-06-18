package ru.toxyxd.yaapi.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val login: String,
    val uid: String,
    val username: String? = null,
)
