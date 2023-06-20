package ru.toxyxd.yaapi.dto.playlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.yaapi.dto.catalog.ActionButtonDto
import ru.toxyxd.yaapi.dto.cover.CoverInfoDto
import ru.toxyxd.yaapi.dto.track.WrappedTrackDto
import ru.toxyxd.yaapi.dto.user.MadeForDto
import ru.toxyxd.yaapi.dto.user.UserDto

@Serializable
data class PlaylistHeaderDto(
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

    @SerialName("description")
    override val description: String? = null,

    @SerialName("descriptionFormatted")
    val descriptionFormatted: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("kind")
    override val kind: String,

    @SerialName("likesCount")
    val likesCount: Int? = null,

    @SerialName("madeFor")
    val madeFor: MadeForDto? = null,

    @SerialName("owner")
    val owner: UserDto? = null,

    @SerialName("revision")
    val revision: Int? = null,

    @SerialName("similarPlaylists")
    val similarPlaylist: List<PlaylistHeaderDto>? = null,

    @SerialName("snapshot")
    val snapshot: Int? = null,

    @SerialName("title")
    override val title: String,

    @SerialName("trackCount")
    val trackCount: Int? = null,

    @SerialName("tracks")
    val tracks: List<WrappedTrackDto>? = null,

    @SerialName("uid")
    override val uid: String,

    @SerialName("visibility")
    val visibility: String? = null,
) : BasePlaylistDto {
    override val itemId: String
        get() = id ?: ("$uid:$kind")
}


