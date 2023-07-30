package ru.toxyxd.item

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.item.component.ToolbarComponent
import ru.toxyxd.item.component.TrackListComponent
import ru.toxyxd.item.component.toolbar.YaToolbarComponent
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
    override val toolbarComponent: ToolbarComponent =
        YaToolbarComponent(viewModel.title, viewModel.coverUrl, childContext("toolbar"))
    override val tracklistComponent: TrackListComponent =
        YaTrackListComponent(viewModel.trackListDto, viewModel.type, childContext("tracklist"))

    init {
        viewModel.load()
    }
}

internal class YaItemRootViewModel(
    private val yaApi: YaApi,
    private val entrypoint: YaApiEntrypoint,
    private val coroutineScope: CoroutineScope
) : InstanceKeeper.Instance, CoroutineScope by coroutineScope {
    val state = MutableValue<ItemRootComponent.State>(ItemRootComponent.State.Loading)
    val type = MutableValue(ItemRootComponent.Type.PLAYLIST)

    val coverUrl = MutableValue("")
    val title = MutableValue("")
    val trackListDto = MutableValue<List<TrackDto>>(emptyList())

    fun load() {
        launch {
            when (entrypoint) {
                is YaApiEntrypoint.YaPlaylistEntrypoint -> playlistTracks(
                    entrypoint.ownerUid,
                    entrypoint.playlistUid
                )

                is YaApiEntrypoint.YaAlbumEntrypoint -> albumTracks(
                    entrypoint.albumUid
                )
            }
        }
    }

    private suspend fun playlistTracks(ownerUid: String, kind: String) {
        return when (val response = yaApi.playlists.getUserPlaylist(ownerUid, kind)) {
            is YaApiResponse.Success -> {
                coverUrl.value = if (response.result.backgroundImageUrl != null) {
                    "https://" + response.result.backgroundImageUrl!!.replace("%%", "700x700")
                } else ""
                title.value = response.result.title

                type.value = ItemRootComponent.Type.PLAYLIST
                trackListDto.value = response.result.tracks?.map { it.track } ?: emptyList()
                state.value = ItemRootComponent.State.Loaded
            }

            is YaApiResponse.InternalError -> throw response.exception
            else -> throw IllegalStateException()
        }
    }

    private suspend fun albumTracks(id: String) {
        return when (val response = yaApi.albums.getAlbum(id)) {
            is YaApiResponse.Success -> {
                coverUrl.value = if (response.result.coverUri != null) {
                    "https://" + response.result.coverUri!!.replace("%%", "700x700")
                } else ""
                title.value = response.result.title

                type.value = ItemRootComponent.Type.ALBUM
                trackListDto.value = response.result.volumes?.flatten() ?: emptyList()
                state.value = ItemRootComponent.State.Loaded
            }

            is YaApiResponse.InternalError -> throw response.exception
            is YaApiResponse.Error -> throw IllegalStateException(response.toString())
            is YaApiResponse.HttpError -> throw IllegalStateException(response.toString())
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}