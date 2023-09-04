package ru.toxyxd.home

import ru.toxyxd.common.HasId
import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.dto.artist.ArtistDto
import ru.toxyxd.yaapi.dto.playlist.BasePlaylistDto
import ru.toxyxd.yaapi.dto.playlist.PlaylistHeaderDto
import ru.toxyxd.yaapi.dto.promotion.PromotionDto

internal sealed class YaLandingEntry : HasId {
    class Recent {
        class Album(
            val dto: AlbumDto
        ) : YaLandingEntry(), HasId by dto

        class Artist(
            val dto: ArtistDto
        ) : YaLandingEntry(), HasId by dto

        class Playlist(
            val dto: BasePlaylistDto
        ) : YaLandingEntry(), HasId by dto
    }

    class Album(
        val dto: AlbumDto
    ) : YaLandingEntry(), HasId by dto

    class PersonalizedPlaylist(
        val dto: BasePlaylistDto
    ) : YaLandingEntry(), HasId by dto
    class Playlist(
        val dto: PlaylistHeaderDto
    ) : YaLandingEntry(), HasId by dto
    class Promotion(
        val dto: PromotionDto
    ) : YaLandingEntry(), HasId by dto

    class Slider(
        override val itemId: String,
        val headerTitle: String,
        val sliderType: SliderComponent.SliderType,
        val items: List<YaLandingEntry>
    ) : YaLandingEntry()

    class Unknown(
        override val itemId: String,
        val type: String,
        val title: String?
    ) : YaLandingEntry(), HasId
}