package ru.toxyxd.yaapi.dto.artist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.dto.cover.CoverPathDto
import ru.toxyxd.yaapi.dto.landing.ActionButtonDto
import ru.toxyxd.yaapi.dto.playlist.PlaylistHeaderDto
import ru.toxyxd.yaapi.dto.track.TrackDto

@Serializable
data class ArtistBriefInfoDto(
    @SerialName("actionButton")
    val actionButton: ActionButtonDto? = null,

    @SerialName("albums")
    val albums: List<AlbumDto>? = null,

    @SerialName("allCovers")
    val allCovers: List<CoverPathDto>? = null,

    @SerialName("alsoAlbums")
    val alsoAlbums: List<AlbumDto>? = null,

    @SerialName("artist")
    val artist: ArtistDto,

    @SerialName("backgroundImageUrl")
    val backgroundImageUrl: String? = null,

    @SerialName("backgroundVideoUrl")
    val backgroundVideoUrl: String? = null,

    @SerialName("discography")
    val discographyAlbums: List<AlbumDto>? = null,

    @SerialName("lastReleaseIds")
    val lastRelease: List<String>? = null,

    @SerialName("links")
    val links: List<LinkDto>? = null,

    @SerialName("playlists")
    val playlists: List<PlaylistHeaderDto>? = null,

    @SerialName("popularTracks")
    val popularTracks: List<TrackDto>? = null,

    @SerialName("similarArtists")
    val similarArtists: List<ArtistDto>? = null,

    @SerialName("stats")
    val stats: ArtistStatsDto? = null,

//    @SerialName("customWave")
//     val vibeButtonInfo: VibeButtonDto? = null,
)


