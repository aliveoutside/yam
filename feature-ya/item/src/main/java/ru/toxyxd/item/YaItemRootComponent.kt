package ru.toxyxd.item

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.item.component.ToolbarComponent
import ru.toxyxd.item.component.TrackListComponent
import ru.toxyxd.item.component.tracklist.YaTrackListComponent
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.track.TrackDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint
import ru.toxyxd.yaapi.internal.YaApiResponse

class YaItemRootComponent(
    yaApi: YaApi,
    entrypoint: YaApiEntrypoint,
    componentContext: ComponentContext,
) : ItemRootComponent, ComponentContext by componentContext {
    private val viewModel = instanceKeeper.getOrCreate {
        YaItemRootViewModel(
            yaApi,
            entrypoint,
            componentCoroutineScope()
        )
    }
    override val state = viewModel.state
    override val toolbarComponent: ToolbarComponent
        get() = TODO()
    override val tracklistComponent: TrackListComponent = YaTrackListComponent(viewModel.tracksDto, componentContext)

    init {
        viewModel.load()
    }
}

internal class YaItemRootViewModel(
    private val yaApi: YaApi,
    private val entrypoint: YaApiEntrypoint,
    coroutineScope: CoroutineScope
) : InstanceKeeper.Instance, CoroutineScope by coroutineScope {
    val state = MutableValue<ItemRootComponent.State>(ItemRootComponent.State.Loading)
    val tracksDto = MutableValue<List<TrackDto>>(emptyList())

    fun load() {
        launch {
            when (entrypoint) {
                is YaApiEntrypoint.YaPlaylistEntrypoint -> playlistTracks(
                    entrypoint.ownerUid,
                    entrypoint.playlistUid
                )

                is YaApiEntrypoint.YaAlbumEntrypoint -> TODO()
            }
        }
    }

    private suspend fun playlistTracks(ownerUid: String, kind: String) {
        return when (val response = yaApi.playlists.getUserPlaylist(ownerUid, kind)) {
            is YaApiResponse.Success -> {
                tracksDto.value = response.result.tracks!!.map { it.track }
                state.value = ItemRootComponent.State.Loaded
            }
            else -> throw IllegalStateException()
        }
    }

    override fun onDestroy() {

    }
}