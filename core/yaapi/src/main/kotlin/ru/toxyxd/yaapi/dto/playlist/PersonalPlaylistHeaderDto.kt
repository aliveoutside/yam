package ru.toxyxd.yaapi.dto.playlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.catalog.ActionButtonDto
import ru.toxyxd.yaapi.dto.cover.CoverInfoDto
import ru.toxyxd.yaapi.dto.track.WrappedTrackDto
import ru.toxyxd.yaapi.dto.user.MadeForDto
import ru.toxyxd.yaapi.dto.user.UserDto

@Serializable
data class PersonalPlaylistHeaderDto(
//    @SerialName("playlistAbsense")
//    val absense: AbsenseFlagDto? = null

    @SerialName("actionButton")
    val actionInfo: ActionButtonDto? = null,

    @SerialName("generatedPlaylistType")
    val autoPlaylistType: String? = null,

    @SerialName("available")
    val available: Boolean? = null,

    @SerialName("backgroundImageUrl")
    val backgroundImageUrl: String? = null,

    @SerialName("backgroundVideoUrl")
    val backgroundVideoUrl: String? = null,

    @SerialName("childContent")
    val childContent: Boolean? = null,

    @SerialName("collective")
    val collective: Boolean? = null,

    @SerialName("containsTrack")
    val containsTrack: Boolean? = null,

    @SerialName("cover")
    override val cover: CoverInfoDto? = null,

    @SerialName("coverWithoutText")
    val coverWithoutText: CoverInfoDto? = null,

//    @SerialName("created")
//    val created: AbstractC6485a.C6486a? = null

    @SerialName("description")
    override val description: String? = null,

    @SerialName("descriptionFormatted")
    val descriptionFormatted: String? = null,

    @SerialName("dummyCover")
    val dummyCover: CoverInfoDto? = null,

    @SerialName("dummyDescription")
    val dummyDescription: String? = null,

    @SerialName("dummyRolloverCover")
    val dummyRolloverCover: CoverInfoDto? = null,

    @SerialName("idForFrom")
    val idForFrom: String? = null,

    @SerialName("kind")
    val kind: String? = null,

    @SerialName("likesCount")
    val likesCount: Int? = null,

    @SerialName("madeFor")
    val madeFor: MadeForDto? = null,

//    @SerialName("modified")
//    val modified: AbstractC6485a.C6486a? = null

    @SerialName("owner")
    val owner: UserDto? = null,

    @SerialName("revision")
    val revision: Int? = null,

    @SerialName("snapshot")
    val snapshot: Int? = null,

    @SerialName("title")
    override val title: String? = null,

    @SerialName("trackCount")
    val trackCount: Int? = null,

    @SerialName("tracks")
    val tracks: List<WrappedTrackDto>? = null,

    @SerialName("uid")
    val uid: String? = null,

//    @SerialName("customWave")
//    val vibeButtonInfo: VibeButtonDto? = null

    @SerialName("visibility")
    val visibility: String? = null
) : BasePlaylistDto {
    override val itemId: String
        get() = uid + kind
}