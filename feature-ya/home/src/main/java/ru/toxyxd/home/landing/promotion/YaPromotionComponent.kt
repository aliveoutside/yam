package ru.toxyxd.home.landing.promotion

import com.arkivanov.decompose.ComponentContext
import ru.toxyxd.common.HasIdComponent
import ru.toxyxd.home.component.promotion.PromotionComponent
import ru.toxyxd.yaapi.dto.promotion.PromotionDto

class YaPromotionComponent(
    dto: PromotionDto,
    componentContext: ComponentContext
) : PromotionComponent, HasIdComponent, ComponentContext by componentContext {
    override val heading = dto.heading
    override val title = dto.title
    override val subtitle = dto.subtitle
    override val imageUrl = "https://" + dto.imageUrl.replace("%%", "576x384")
    override val itemId: String = dto.itemId
}