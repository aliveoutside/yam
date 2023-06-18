package ru.toxyxd.yaapi.delegates

import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.catalog.CatalogRootDto

@JvmInline
value class YaLanding(
    private val yaApi: YaApi
) {
    suspend fun getLanding() = yaApi.api<CatalogRootDto>(listOf("landing3")) {
        param("blocks", "promotions,personal-playlists,banner,play_contexts")
    }
}