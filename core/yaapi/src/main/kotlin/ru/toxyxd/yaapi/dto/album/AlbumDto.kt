package ru.toxyxd.yaapi.dto.album

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.artist.ArtistDto
import ru.toxyxd.yaapi.dto.track.TrackDto

@Serializable
data class AlbumDto(
//    @SerialName("actionButton")
//    val actionButton: ActionButtonDto? = null,

    @SerialName("artists")
    val artists: List<ArtistDto>,

    @SerialName("available")
    val available: Boolean? = null,

    @SerialName("availableForOptions")
    val availableForOptions: List<String>? = null,

    @SerialName("availableForPremiumUsers")
    val availableForPremiumUsers: Boolean? = null,

    @SerialName("availablePartially")
    val availablePartially: Boolean? = null,

    @SerialName("backgroundImageUrl")
    val backgroundImageUrl: String? = null,

    @SerialName("backgroundVideoUrl")
    val backgroundVideoUrl: String? = null,

    @SerialName("childContent")
    val childContent: Boolean? = null,

    @SerialName("contentWarning")
    val contentWarning: String? = null,

    @SerialName("coverUri")
    val coverUri: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("disclaimers")
    val disclaimer: List<String>? = null,

    @SerialName("duplicates")
    val duplicates: List<AlbumDto>? = null,

    @SerialName("durationLeft")
    val durationLeft: Int? = null,

    @SerialName("durationSec")
    val durationSec: Int? = null,

    @SerialName("genre")
    val genre: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("likesCount")
    val likesCount: Int? = null,

    @SerialName("metaType")
    val metaType: String? = null,

    @SerialName("originalReleaseYear")
    val originalReleaseYear: String? = null,

    @SerialName("shortDescription")
    val shortDescription: String? = null,

    @SerialName("sortOrder")
    val sortOrder: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("trackCount")
    val trackCount: Int? = null,

    @SerialName("trackPosition")
    val trackPosition: TrackPositionDto? = null,

    @SerialName("tracks")
    val tracks: List<TrackDto>? = null,

    @SerialName("type")
    val type: String? = null,

//@SerialName("customWave")
//val vibeButton: VibeButtonDto? = null,

    @SerialName("volumes")
    val volumes: List<List<TrackDto>>? = null,

    @SerialName("year")
    val year: String? = null
) : HasId {
    override val itemId: String
        get() = id!!
}
