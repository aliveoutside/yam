package ru.toxyxd.yaapi.delegates

import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.album.AlbumDto

@JvmInline
value class YaAlbums(
    val yaApi: YaApi
) {
    suspend fun getAlbum(albumId: String) = yaApi.api<AlbumDto>(listOf("albums", albumId, "with-tracks")) {}
}