package ru.toxyxd.yaapi.dto.landing

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

@Serializable(with = LandingBlockEntityDtoSerializer::class)
sealed class LandingBlockEntityDto<out T : BaseDto> : HasId {
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
    ) : LandingBlockEntityDto<AlbumDto>()

    @Serializable
    class PersonalPlaylistBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.PERSONAL_PLAYLIST,
        override val data: PersonalPlaylistHeaderMetaDto
    ) : LandingBlockEntityDto<PersonalPlaylistHeaderMetaDto>()

    @Serializable
    class PlayContextBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.PLAY_CONTEXT,
        override val data: RecentlyDto
    ) : LandingBlockEntityDto<RecentlyDto>()

    @Serializable
    class PromotionBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.PROMOTION,
        override val data: PromotionDto
    ) : LandingBlockEntityDto<PromotionDto>()

    @Serializable
    class UnknownBlockEntityDto(
        override val itemId: String,
        override val type: Type = Type.UNKNOWN,
        override val data: BaseDto
    ) : LandingBlockEntityDto<BaseDto>()
}

object LandingBlockEntityDtoSerializer : KSerializer<LandingBlockEntityDto<BaseDto>> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("LandingBlockEntityDtoSerializer")

    override fun deserialize(decoder: Decoder): LandingBlockEntityDto<BaseDto> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val id = element.jsonObject["id"]!!.jsonPrimitive.content
        val type = element.jsonObject["type"]!!.jsonPrimitive.content
        val data = element.jsonObject["data"]!!.jsonObject

        val typeEnum: LandingBlockEntityDto.Type

        try {
            typeEnum = LandingBlockEntityDto.Type.valueOf(
                type.replace("-", "_")
                    .uppercase()
            )
        } catch (e: IllegalArgumentException) {
            Log.w(
                "LandingBlockEntityDtoSerializer",
                "Unknown type: $type, id: $id, data: $data"
            )
            Log.e("LandingBlockEntityDtoSerializer", e.toString())
            return LandingBlockEntityDto.UnknownBlockEntityDto(
                id,
                LandingBlockEntityDto.Type.UNKNOWN,
                object : BaseDto {}
            )
        }

        return when (typeEnum) {
            LandingBlockEntityDto.Type.ALBUM -> LandingBlockEntityDto.AlbumBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(AlbumDto.serializer(), data)
            )

            LandingBlockEntityDto.Type.PLAY_CONTEXT -> LandingBlockEntityDto.PlayContextBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(RecentlyDto.serializer(), data)
            )

            LandingBlockEntityDto.Type.PERSONAL_PLAYLIST -> LandingBlockEntityDto.PersonalPlaylistBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(PersonalPlaylistHeaderMetaDto.serializer(), data)
            )

            LandingBlockEntityDto.Type.PROMOTION -> LandingBlockEntityDto.PromotionBlockEntityDto(
                id,
                typeEnum,
                decoder.json.decodeFromJsonElement(PromotionDto.serializer(), data)
            )

            else -> {
                throw SerializationException("Unknown type: $type, id: $id, data: $data")
            }
        }
    }

    override fun serialize(encoder: Encoder, value: LandingBlockEntityDto<BaseDto>) {
        throw NotImplementedError()
    }
}