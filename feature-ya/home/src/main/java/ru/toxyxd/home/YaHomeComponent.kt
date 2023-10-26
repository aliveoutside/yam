package ru.toxyxd.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.toxyxd.common.HasIdComponent
import ru.toxyxd.common.childList
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.home.landing.common.YaAlbumComponent
import ru.toxyxd.home.landing.common.YaPlaylistComponent
import ru.toxyxd.home.landing.common.YaRecentAlbumComponent
import ru.toxyxd.home.landing.common.YaRecentArtistComponent
import ru.toxyxd.home.landing.common.YaRecentPlaylistComponent
import ru.toxyxd.home.landing.promotion.YaPromotionComponent
import ru.toxyxd.home.landing.slider.YaSliderComponent
import ru.toxyxd.home.landing.unknown.YaUnknownComponent
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.internal.YaApiEntrypoint
import ru.toxyxd.yaapi.internal.YaApiResponse

class YaHomeComponent(
    private val yaApi: YaApi,
    private val onItemClicked: (YaApiEntrypoint) -> Unit,
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext,
    CoroutineScope by componentContext.componentCoroutineScope() {
    private val entries = MutableValue<List<YaLandingEntry>>(emptyList())
    private val binder = YaLandingBinder()

    override val children = childList(
        state = entries,
        childFactory = ::entryToComponent,
    )

    init {
        launch {
            load()
        }
    }

    private fun entryToComponent(
        entry: YaLandingEntry,
        childContext: ComponentContext
    ): HasIdComponent = when (entry) {
        is YaLandingEntry.Slider -> YaSliderComponent(
            itemId = entry.itemId,
            headerTitle = entry.headerTitle,
            items = entry.items.map { sliderEntry ->
                entryToComponent(sliderEntry, childContext.childContext(sliderEntry.itemId + childContext.toString()))
            },
            sliderType = entry.sliderType,
            componentContext = childContext
        )

        is YaLandingEntry.Recent.Album -> YaRecentAlbumComponent(
            dto = entry.dto,
            onItemClicked = onItemClicked,
            componentContext = childContext
        )

        is YaLandingEntry.Recent.Artist -> YaRecentArtistComponent(
            dto = entry.dto,
            componentContext = childContext
        )

        is YaLandingEntry.Recent.Playlist -> YaRecentPlaylistComponent(
            dto = entry.dto,
            onItemClicked = onItemClicked,
            componentContext = childContext
        )

        is YaLandingEntry.Album -> YaAlbumComponent(
            dto = entry.dto,
            onItemClicked = onItemClicked,
            componentContext = childContext
        )

        is YaLandingEntry.PersonalizedPlaylist -> YaPlaylistComponent(
            dto = entry.dto,
            onItemClicked = onItemClicked,
            componentContext = childContext
        )

        is YaLandingEntry.Playlist -> YaPlaylistComponent(
            dto = entry.dto,
            onItemClicked = onItemClicked,
            componentContext = childContext
        )

        is YaLandingEntry.Promotion -> YaPromotionComponent(
            dto = entry.dto,
            componentContext = childContext
        )

        is YaLandingEntry.Unknown -> YaUnknownComponent(
            componentContext = childContext,
            id = entry.itemId,
            type = entry.type,
            title = entry.title
        )
    }

    private suspend fun load() = withContext(Dispatchers.IO) {
        when (val response = yaApi.landing.getLanding()) {
            is YaApiResponse.Success -> {
                entries.value = binder.transform(response.result)
            }

            is YaApiResponse.InternalError -> {
                throw response.exception
            }

            is YaApiResponse.HttpError -> {
                throw IllegalStateException("Code: ${response.code}, description: ${response.description}")
            }

            else -> {
                throw IllegalStateException("Unknown response type: $response")
            }
        }
    }
}