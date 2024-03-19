package ru.toxyxd.artist.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import ru.toxyxd.common.CoverUtil
import ru.toxyxd.yaapi.dto.artist.ArtistBriefInfoDto

class YaToolbarComponent(
    artistInfo: ArtistBriefInfoDto,
    val onGoBack: () -> Unit,
    componentContext: ComponentContext
) : ToolbarComponent, ComponentContext by componentContext{
    override val coverUrl = MutableValue(artistInfo.artist.cover?.uri?.let { CoverUtil.getMediumCover(it)} ?: "")
    override val title = MutableValue(artistInfo.artist.name)

    override fun onBackClicked() {
        onGoBack()
    }
}