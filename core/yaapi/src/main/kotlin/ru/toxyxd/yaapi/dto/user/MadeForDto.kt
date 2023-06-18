package ru.toxyxd.yaapi.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MadeForDto(
    val caseForms: UserCaseFormsDto,
    @SerialName("userInfo")
    val userInfo: UserDto
)
