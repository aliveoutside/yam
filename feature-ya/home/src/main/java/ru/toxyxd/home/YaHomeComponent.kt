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
import ru.toxyxd.home.catalog.common.YaAlbumComponent
import ru.toxyxd.home.catalog.common.YaPlaylistComponent
import ru.toxyxd.home.catalog.promotion.YaPromotionComponent
import ru.toxyxd.home.catalog.slider.YaSliderComponent
import ru.toxyxd.home.catalog.unknown.YaUnknownComponent
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.internal.YaApiResponse

class YaHomeComponent(
    private val yaApi: YaApi,
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext,
    CoroutineScope by componentContext.componentCoroutineScope() {
    private val entries = MutableValue<List<YaCatalogEntry>>(emptyList())
    private val binder = YaCatalogBinder()

    override val children = childList(
        state = entries,
        childFactory = ::entryAsComponent,
    )

    init {
        launch {
            load()
        }
    }

    private fun entryAsComponent(
        entry: YaCatalogEntry,
        childContext: ComponentContext
    ): HasIdComponent = when (entry) {
        is YaCatalogEntry.Slider -> YaSliderComponent(
            itemId = entry.itemId,
            headerTitle = entry.headerTitle,
            items = entry.items.map { sliderEntry ->
                entryAsComponent(sliderEntry, childContext.childContext(sliderEntry.itemId))
            },
            sliderType = entry.sliderType,
            componentContext = childContext
        )

        is YaCatalogEntry.Album -> YaAlbumComponent(
            dto = entry.dto,
            componentContext = childContext
        )

        is YaCatalogEntry.Playlist -> YaPlaylistComponent(
            dto = entry.dto,
            componentContext = childContext
        )

        is YaCatalogEntry.Promotion -> YaPromotionComponent(
            dto = entry.dto,
            componentContext = childContext
        )

        is YaCatalogEntry.Unknown -> YaUnknownComponent(
            componentContext = childContext,
            id = entry.itemId,
            type = entry.type,
            title = entry.title
        )
    }

    private suspend fun load() = withContext(Dispatchers.IO) {
        val response = yaApi.landing.getLanding()

        when (response) {
            is YaApiResponse.Success -> {
                entries.value = binder.transform(response.result)
            }

            is YaApiResponse.InternalError -> {
                throw response.exception
            }
            else -> {
                throw IllegalStateException("Unknown response type: $response")
            }
        }
    }
}