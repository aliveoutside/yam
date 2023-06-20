package ru.toxyxd.yaapi.delegates

import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.playlist.PlaylistHeaderDto

@JvmInline
value class YaPlaylists(
    private val yaApi: YaApi
) {
    suspend fun getUserPlaylist(ownerUid: String, kind: String) = yaApi.api<PlaylistHeaderDto>(
        path = listOf("users", ownerUid, "playlists", kind),
    ) { param("rich-tracks", "true") }
}