package ru.toxyxd.yaapi.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserCaseFormsDto(
    val accusative: String? = null,
    val dative: String? = null,
    val genitive: String? = null,
    val instrumental: String? = null,
    val nominative: String? = null,
    val prepositional: String? = null,
)
