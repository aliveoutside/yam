package ru.toxyxd.yaapi.dto.track

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.dto.artist.ArtistDto

@Serializable
data class TrackDto(
    @SerialName("albums")
    val albums: List<AlbumDto>? = null,

    @SerialName("artists")
    val artists: List<ArtistDto>? = null,

    @SerialName("available")
    val available: Boolean? = null,

    @SerialName("availableForOptions")
    val availableForOptions: List<String>? = null,

    @SerialName("availableForPremiumUsers")
    val availableForPremiumUsers: Boolean? = null,

    @SerialName("availableFullWithoutPermission")
    val availableFullWithoutPermission: Boolean? = null,

    @SerialName("backgroundVideoUri")
    val backgroundVideoUri: String? = null,

    @SerialName("best")
    val best: Boolean? = null,

    @SerialName("contentWarning")
    val contentWarning: String? = null,

    @SerialName("coverUri")
    val coverUri: String? = null,

    @SerialName("disclaimers")
    val disclaimer: List<String>? = null,

    @SerialName("durationMs")
    val durationMs: Long? = null,

    @SerialName("podcastEpisodeType")
    val episodeType: String? = null,

    @SerialName("error")
    val error: String? = null,

    @SerialName("fade")
    val fade: TrackFadeDto? = null,

    @SerialName("id")
    /* renamed from: id */
    val id: String? = null,

    @SerialName("isSuitableForChildren")
    val isSuitableForChildren: Boolean? = null,

    @SerialName("lyricsAvailable")
    val lyricsAvailable: Boolean? = null,

    @SerialName("lyricsInfo")
    val lyricsInfo: LyricsInfoDto? = null,

    @SerialName("playerId")
    val playerId: String? = null,

    @SerialName("previewDurationMs")
    val previewDurationMs: Long? = null,

    @SerialName("pubDate")
    val pubDate: String? = null,

    @SerialName("realId")
    val realId: String? = null,

    @SerialName("rememberPosition")
    val rememberPosition: Boolean? = null,

    @SerialName("shortDescription")
    val shortDescription: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("trackSource")
    val trackSource: String? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("userInfo")
    val userInfo: TrackUserInfoDto? = null,

    @SerialName("version")
    val version: String? = null
) {
    @Serializable
    data class LyricsInfoDto(
        val hasAvailableSyncLyrics: Boolean? = null,
        val hasAvailableTextLyrics: Boolean? = null,
    )
}


