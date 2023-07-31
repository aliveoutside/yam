package ru.toxyxd.yaapi.dto.recently

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.toxyxd.yaapi.dto.BaseDto
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.dto.artist.ArtistDto
import ru.toxyxd.yaapi.dto.playlist.PlaylistHeaderDto

@Serializable(with = RecentlyDtoSerializer::class)
sealed class RecentlyDto : BaseDto {
    abstract val client: String
    abstract val context: String
    abstract val contextItem: String

    @Serializable
    class RecentlyAlbumDto(
        override val client: String,
        override val context: String,
        override val contextItem: String,
        @SerialName("payload")
        val album: AlbumDto
    ) : RecentlyDto()

    @Serializable
    class RecentlyArtistDto(
        override val client: String,
        override val context: String,
        override val contextItem: String,
        @SerialName("payload")
        val artist: ArtistDto
    ) : RecentlyDto()

    @Serializable
    class RecentlyPlaylistDto(
        override val client: String,
        override val context: String,
        override val contextItem: String,
        @SerialName("payload")
        val playlist: PlaylistHeaderDto
    ) : RecentlyDto()
}

object RecentlyDtoSerializer : KSerializer<RecentlyDto> {
    override val descriptor: SerialDescriptor = RecentlyDto.serializer().descriptor

    override fun deserialize(decoder: Decoder): RecentlyDto {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected Json Input")
        val json = input.decodeJsonElement().jsonObject

        val client = json["client"]?.jsonPrimitive?.content
            ?: throw SerializationException("Expected client")
        val context = json["context"]?.jsonPrimitive?.content
            ?: throw SerializationException("Expected context")
        val contextItem = json["contextItem"]?.jsonPrimitive?.content
            ?: throw SerializationException("Expected contextItem")

        val payload =
            json["payload"]?.jsonObject ?: throw SerializationException("Expected payload")

        return when (context) {
            "album" -> RecentlyDto.RecentlyAlbumDto(
                client = client,
                context = context,
                contextItem = contextItem,
                album = input.json.decodeFromJsonElement(AlbumDto.serializer(), payload)
            )

            "artist" -> RecentlyDto.RecentlyArtistDto(
                client = client,
                context = context,
                contextItem = contextItem,
                artist = input.json.decodeFromJsonElement(ArtistDto.serializer(), payload)
            )

            "playlist" -> RecentlyDto.RecentlyPlaylistDto(
                client = client,
                context = context,
                contextItem = contextItem,
                playlist = input.json.decodeFromJsonElement(PlaylistHeaderDto.serializer(), payload)
            )

            else -> throw SerializationException("Unknown type: $context")
        }
    }

    override fun serialize(encoder: Encoder, value: RecentlyDto) {
        throw SerializationException("RecentlyDto is not serializable")
    }
}