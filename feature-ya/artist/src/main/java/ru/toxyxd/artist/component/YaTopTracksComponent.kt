package ru.toxyxd.artist.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import ru.toxyxd.item.component.tracklist.YaTopTrackComponent
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto

class YaTopTracksComponent(
    private val artistInfo: ArtistBriefInfoDto,
    private val onPlayerEvent: (PlayerComponent.Event) -> Unit,
    componentContext: ComponentContext
) : TopTracksComponent, ComponentContext by componentContext {
    override val tracks = artistInfo.popularTracks?.let {
        MutableValue(it.mapIndexed { index, track -> YaTopTrackComponent(track, index, childContext("track${index}")) })
    }

    override fun play(index: Int) {
        if (tracks != null) {
            val event = PlayerComponent.Event.Play(
                tracks.value,
                index,
                artistInfo.artist.id
            )
            onPlayerEvent(event)
        }
    }
}

