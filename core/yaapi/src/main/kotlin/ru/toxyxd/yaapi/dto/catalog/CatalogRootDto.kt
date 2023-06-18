package ru.toxyxd.yaapi.dto.catalog

import kotlinx.serialization.Serializable

@Serializable
data class CatalogRootDto(
    val blocks: List<CatalogBlockDto>
)
