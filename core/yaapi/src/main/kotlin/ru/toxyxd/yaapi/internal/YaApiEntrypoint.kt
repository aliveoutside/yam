package ru.toxyxd.yaapi.internal

import kotlinx.serialization.Serializable

@Serializable
sealed class YaApiEntrypoint {
    @Serializable
    class YaPlaylistEntrypoint(val ownerUid: String, val playlistUid: String) : YaApiEntrypoint()

    @Serializable
    class YaAlbumEntrypoint(val albumUid: String) : YaApiEntrypoint()

    @Serializable
    class YaArtistEntrypoint(val artistUid: String) : YaApiEntrypoint()
}
