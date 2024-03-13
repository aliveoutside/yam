package ru.toxyxd.yaapi.delegates

import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto

@JvmInline
value class YaArtists(
    val yaApi: YaApi
) {
    suspend fun getArtistBriefInfo(artistId: String) =
        yaApi.api<ArtistBriefInfoDto>(listOf("artists", artistId, "brief-info")) {
            param("discographyBlockEnabled", "true")
            param("popularTracksCount", "5")
        }
}