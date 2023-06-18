package ru.toxyxd.yaapi.dto.playlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class PersonalPlaylistMetaDto {
    @SerialName("description")
    abstract val description: List<String>?

    @SerialName("notify")
    abstract val notify: Boolean

    @SerialName("previewDescription")
    abstract val previewDescription: String

    @SerialName("ready")
    abstract val ready: Boolean

    @SerialName("type")
    abstract val type: String
}