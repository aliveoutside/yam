package ru.toxyxd.yaapi.dto.catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.BaseDto

@Serializable
class CatalogBlockDto(
    @SerialName("id")
    override val itemId: String,
    val type: Type = Type.Unknown,
    val title: String? = null,
    val description: String? = null,
    val entities: List<CatalogBlockEntityDto<BaseDto>>,
) : HasId {

    @Serializable
    enum class Type {
        @SerialName("mixes")
        Mixes,

        @SerialName("new-releases")
        NewReleases,

        @SerialName("personal-playlists")
        PersonalPlaylists,

        @SerialName("play-contexts")
        PlayContexts,

        @SerialName("playlist-with-likes")
        PlaylistWithLikes,

        @SerialName("promotions")
        Promotions,

        @SerialName("tabs")
        Tabs,

        Unknown
    }
}

