package ru.toxyxd.artist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import ru.toxyxd.common.componentCoroutineScope
import ru.toxyxd.player.PlayerComponent
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto
import ru.toxyxd.yaapi.internal.YaApiEntrypoint
import ru.toxyxd.yaapi.internal.YaApiResponse

class YaArtistRootComponent(
    yaApi: YaApi,
    artistId: String,
    onArtistClicked: (String) -> Unit,
    onItemClicked: (YaApiEntrypoint) -> Unit,
    onGoBack: () -> Unit,
    onPlayerEvent: (PlayerComponent.Event) -> Unit,
    componentContext: ComponentContext,
) : ArtistRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    private val _stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Loading,
    ) { config, componentContext ->
        when (config) {
            is Config.Loading -> {
                ArtistRootComponent.Child.Loading
            }
            is Config.Loaded -> {
                ArtistRootComponent.Child.Loaded(
                    YaArtistComponent(
                        artistInfo = config.artistInfo,
                        onArtistClicked = onArtistClicked,
                        onItemClicked = onItemClicked,
                        onGoBack = onGoBack,
                        onPlayerEvent = onPlayerEvent,
                        componentContext = componentContext
                    )
                )
            }
        }
    }

    override val stack: Value<ChildStack<*, ArtistRootComponent.Child>> = _stack

    init {
        lifecycle.doOnCreate {
            componentCoroutineScope().launch {
                when (val artistInfo = yaApi.artists.getArtistBriefInfo(artistId)) {
                    is YaApiResponse.Success -> {
                        onStackLoaded(artistInfo.result)
                    }

                    is YaApiResponse.Error -> {
                        throw RuntimeException("Failed to load artist info: ${artistInfo.error}")
                    }

                    is YaApiResponse.HttpError -> {
                        throw RuntimeException("Failed to load artist info: ${artistInfo.code} ${artistInfo.description}")
                    }

                    is YaApiResponse.InternalError -> {
                        throw artistInfo.exception
                    }
                }
            }
        }
    }

    private fun onStackLoaded(artistInfo: ArtistBriefInfoDto) {
        navigation.pushNew(Config.Loaded(artistInfo))
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Loading : Config
        @Serializable
        data class Loaded(val artistInfo: ArtistBriefInfoDto) : Config
    }
}