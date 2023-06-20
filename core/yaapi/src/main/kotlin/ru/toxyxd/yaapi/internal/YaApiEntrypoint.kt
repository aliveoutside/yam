package ru.toxyxd.yaapi.internal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class YaApiEntrypoint : Parcelable {
    class YaPlaylistEntrypoint(val ownerUid: String, val playlistUid: String) : YaApiEntrypoint()
    class YaAlbumEntrypoint(val albumUid: String) : YaApiEntrypoint()
}
