package ru.toxyxd.artist.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.home.landing.common.YaAlbumComponent
import ru.toxyxd.home.landing.slider.YaSliderComponent
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint

class YaAlbumsComponent(
    artistInfo: ArtistBriefInfoDto,
    onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : AlbumsComponent, ComponentContext by componentContext {
    override val albumsSlider: Value<SliderComponent>? = artistInfo.albums?.let { albumsDtos ->
        MutableValue(
            YaSliderComponent(
                artistInfo.artist.id,
                albumsDtos.map { albumDto ->
                    YaAlbumComponent(albumDto, onItemClicked, childContext(albumDto.id))
                },
                SliderComponent.SliderType.Horizontal,
                "Альбомы",
                childContext("albumsSlider")
            )
        )
    }
}