package ru.toxyxd.yaapi.dto.artist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.cover.CoverPathDto

@Serializable
data class ArtistDto(
    @SerialName("available")
    val available: Boolean? = null,

    @SerialName("childContent")
    val childContent: Boolean? = null,

    @SerialName("composer")
    val composer: Boolean? = null,

    @SerialName("counts")
    val counts: ArtistCountsDto? = null,

    @SerialName("cover")
    val cover: CoverPathDto? = null,

    @SerialName("coverUri")
    val coverUri: String? = null,

//    @SerialName("decomposed")
//    val decomposed: DecomposedDto? = null,

    @SerialName("description")
    val description: ArtistDescriptionDto? = null,

    @SerialName("disclaimers")
    val disclaimer: List<String>? = null,

    @SerialName("id")
    val id: String,

    @SerialName("likesCount")
    val likesCount: Int? = null,

    @SerialName("links")
    val links: List<LinkDto>? = null,

    @SerialName("name")
    val name: String,

    @SerialName("various")
    val various: Boolean? = null,
) : HasId {
    override val itemId: String
        get() = id
}