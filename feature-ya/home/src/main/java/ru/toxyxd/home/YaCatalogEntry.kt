package ru.toxyxd.home

import ru.toxyxd.common.HasId
import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.dto.playlist.BasePlaylistDto
import ru.toxyxd.yaapi.dto.promotion.PromotionDto

internal sealed class YaCatalogEntry : HasId {
    class Recent {
        class Album(
            val dto: AlbumDto
        ) : YaCatalogEntry(), HasId by dto
        class Playlist(
            val dto: BasePlaylistDto
        ) : YaCatalogEntry(), HasId by dto
    }
    class PersonalizedPlaylist(
        val dto: BasePlaylistDto
    ) : YaCatalogEntry(), HasId by dto
    class Promotion(
        val dto: PromotionDto
    ) : YaCatalogEntry(), HasId by dto

    class Slider(
        override val itemId: String,
        val headerTitle: String,
        val sliderType: SliderComponent.SliderType,
        val items: List<YaCatalogEntry>
    ) : YaCatalogEntry()

    class Unknown(
        override val itemId: String,
        val type: String,
        val title: String?
    ) : YaCatalogEntry(), HasId
}