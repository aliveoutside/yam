package ru.toxyxd.yaapi.dto.artist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistCountsDto(
    @SerialName("alsoAlbums")
    val alsoAlbums: Int? = null,

    @SerialName("directAlbums")
    val directAlbums: Int? = null,

    @SerialName("discographyAlbums")
    val discography: Int? = null,

    @SerialName("tracks")
    val tracks: Int? = null
)

