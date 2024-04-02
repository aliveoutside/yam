package ru.toxyxd.item

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.toxyxd.common.CoverUtil
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.item.component.ToolbarComponent
import ru.toxyxd.item.component.TrackListComponent
import ru.toxyxd.item.component.toolbar.YaToolbarComponent
import ru.toxyxd.item.component.tracklist.YaTrackListComponent
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.album.AlbumDto
import ru.toxyxd.yaapi.dto.playlist.PlaylistHeaderDto
import ru.toxyxd.yaapi.dto.track.TrackDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint
import ru.toxyxd.yaapi.internal.YaApiResponse

class YaItemRootComponent(
    yaApi: YaApi,
    entrypoint: YaApiEntrypoint,
    onGoBack: () -> Unit,
    onPlayerEvent: (PlayerComponent.Event) -> Unit,
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
        YaToolbarComponent(
            viewModel.title,
            viewModel.subtitle,
            viewModel.coverUrl,
            onGoBack,
            childContext("toolbar")
        )
    override val tracklistComponent: TrackListComponent =
        YaTrackListComponent(viewModel.trackListDto, viewModel.type, viewModel.playedFromId, onPlayerEvent, childContext("tracklist"))

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
    var subtitle = MutableValue("")
    val playedFromId = MutableValue("")
    val trackListDto = MutableValue<List<TrackDto>>(emptyList())

    fun load() {
        launch {
            when (entrypoint) {
                is YaApiEntrypoint.YaPlaylistEntrypoint -> loadPlaylistTracks(
                    entrypoint.ownerUid,
                    entrypoint.playlistUid
                )

                is YaApiEntrypoint.YaAlbumEntrypoint -> loadAlbumTracks(
                    entrypoint.albumUid
                )

                else -> throw IllegalStateException("Unsupported entrypoint type: $entrypoint")
            }
        }
    }

    private suspend fun loadPlaylistTracks(ownerUid: String, kind: String) {
        return when (val response = yaApi.playlists.getUserPlaylist(ownerUid, kind)) {
            is YaApiResponse.Success -> {
                updatePlaylistData(response.result)
            }

            is YaApiResponse.InternalError -> throw response.exception
            else -> throw IllegalStateException()
        }
    }

    private suspend fun loadAlbumTracks(id: String) {
        return when (val response = yaApi.albums.getAlbum(id)) {
            is YaApiResponse.Success -> {
                updateAlbumData(response.result)
            }

            is YaApiResponse.InternalError -> throw response.exception
            is YaApiResponse.Error -> throw IllegalStateException(response.toString())
            is YaApiResponse.HttpError -> throw IllegalStateException(response.toString())
        }
    }

    private fun updatePlaylistData(playlist: PlaylistHeaderDto) {
        coverUrl.value = playlist.backgroundImageUrl?.let { CoverUtil.getLargeCover(it) } ?: ""
        subtitle.value = playlist.description ?: ""
        title.value = playlist.title
        playedFromId.value = playlist.kind
        type.value = ItemRootComponent.Type.PLAYLIST
        trackListDto.value = playlist.tracks?.map { it.track } ?: emptyList()
        state.value = ItemRootComponent.State.Loaded
    }

    private fun updateAlbumData(album: AlbumDto) {
        coverUrl.value = album.coverUri?.let { CoverUtil.getLargeCover(it) } ?: ""
        title.value = album.title
        playedFromId.value = album.id
        type.value = ItemRootComponent.Type.ALBUM
        trackListDto.value = album.volumes?.flatten() ?: emptyList()
        state.value = ItemRootComponent.State.Loaded
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}