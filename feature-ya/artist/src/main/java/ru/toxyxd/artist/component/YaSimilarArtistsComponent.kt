package ru.toxyxd.artist.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.toxyxd.home.component.slider.SliderComponent
import ru.toxyxd.home.landing.common.YaRecentArtistComponent
import ru.toxyxd.home.landing.slider.YaSliderComponent
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto

class YaSimilarArtistsComponent(
    artistInfo: ArtistBriefInfoDto,
    onArtistClicked: (String) -> Unit,
    componentContext: ComponentContext
) : SimilarArtistsComponent, ComponentContext by componentContext {
    override val artistsSlider: Value<SliderComponent>? = artistInfo.similarArtists?.let { artistDtos ->
        MutableValue(
            YaSliderComponent(
                artistInfo.artist.id,
                artistDtos.map { artistDto ->
                    YaRecentArtistComponent(artistDto, onArtistClicked, childContext(artistDto.id))
                },
                SliderComponent.SliderType.Horizontal,
                "Похожие",
                childContext("similarArtistsSlider")
            )
        )
    }
}