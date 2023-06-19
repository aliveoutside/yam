package ru.toxyxd.yaapi.dto.catalog

import android.util.Log
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.BaseDto
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.dto.playlist.PersonalPlaylistHeaderMetaDto
import ru.toxyxd.yaapi.dto.promotion.PromotionDto
import ru.toxyxd.yaapi.dto.recently.RecentlyDto

@Serializable(with = CatalogBlockEntityDtoSerializer::class)
sealed class CatalogBlockEntityDto<out T : BaseDto> : HasId {
    @SerialName("id")
    abstract override val itemId: String
    abstract val type: Type
    abstract val data: T

    enum class Type {
        @SerialName("album")
        ALBUM,

        @SerialName("personal-playlist")
        PERSONAL_PLAYLIST,

        @SerialName("play-context")
        PLAY_CONTEXT,

        @SerialName("promotion")
        PROMOTION,

        @SerialName("Unknown")
        UNKNOWN
    }

    @Serializable
    class AlbumBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.ALBUM,
        override val data: AlbumDto
    ) : CatalogBlockEntityDto<AlbumDto>()

    @Serializable
    class PersonalPlaylistBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.PERSONAL_PLAYLIST,
        override val data: PersonalPlaylistHeaderMetaDto
    ) : CatalogBlockEntityDto<PersonalPlaylistHeaderMetaDto>()

    @Serializable
    class PlayContextBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.PLAY_CONTEXT,
        override val data: RecentlyDto
    ) : CatalogBlockEntityDto<RecentlyDto>()

    @Serializable
    class PromotionBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.PROMOTION,
        override val data: PromotionDto
    ) : CatalogBlockEntityDto<PromotionDto>()

    @Serializable
    class UnknownBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.UNKNOWN,
        override val data: BaseDto
    ) : CatalogBlockEntityDto<BaseDto>()
}

object CatalogBlockEntityDtoSerializer : KSerializer<CatalogBlockEntityDto<BaseDto>> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CatalogBlockEntityDtoSerializer")

    override fun deserialize(decoder: Decoder): CatalogBlockEntityDto<BaseDto> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val id = element.jsonObject["id"]!!.jsonPrimitive.content
        val type = element.jsonObject["type"]!!.jsonPrimitive.content
        val data = element.jsonObject["data"]!!.jsonObject

        val typeEnum: CatalogBlockEntityDto.Type

        try {
            typeEnum = CatalogBlockEntityDto.Type.valueOf(
                type.replace("-", "_")
                    .uppercase()
            )
        } catch (e: IllegalArgumentException) {
            Log.w(
                "CatalogBlockEntityDtoSerializer",
                "Unknown type: $type, id: $id, data: $data"
            )
            Log.e("CatalogBlockEntityDtoSerializer", e.toString())
            return CatalogBlockEntityDto.UnknownBlockEntityDto(
                id,
                CatalogBlockEntityDto.Type.UNKNOWN,
                object : BaseDto {}
            )
        }

        return when (typeEnum) {
            CatalogBlockEntityDto.Type.ALBUM -> CatalogBlockEntityDto.AlbumBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(AlbumDto.serializer(), data)
            )

            CatalogBlockEntityDto.Type.PLAY_CONTEXT -> CatalogBlockEntityDto.PlayContextBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(RecentlyDto.serializer(), data)
            )

            CatalogBlockEntityDto.Type.PERSONAL_PLAYLIST -> CatalogBlockEntityDto.PersonalPlaylistBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(PersonalPlaylistHeaderMetaDto.serializer(), data)
            )

            CatalogBlockEntityDto.Type.PROMOTION -> CatalogBlockEntityDto.PromotionBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(PromotionDto.serializer(), data)
            )

            else -> {
                throw SerializationException("Unknown type: $type, id: $id, data: $data")
            }
        }
    }

    override fun serialize(encoder: Encoder, value: CatalogBlockEntityDto<BaseDto>) {
        throw NotImplementedError()
    }
}