package ru.toxyxd.yaapi.dto.playlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.yaapi.dto.BaseDto

@Serializable
data class PersonalPlaylistHeaderMetaDto(
    override val description: List<String>? = null,
    override val notify: Boolean,
    override val previewDescription: String? = null,
    override val ready: Boolean,
    override val type: String,
    @SerialName("data")
    val playlistHeader: PersonalPlaylistHeaderDto
) : PersonalPlaylistMetaDto(), BaseDto