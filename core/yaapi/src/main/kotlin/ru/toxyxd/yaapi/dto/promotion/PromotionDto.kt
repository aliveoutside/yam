package ru.toxyxd.yaapi.dto.promotion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.BaseDto

@Serializable
data class PromotionDto(
    val heading: String,
    @SerialName("image")
    val imageUrl: String,
    val promoId: String,
    val subtitle: String,
    val title: String,
    val urlScheme: String
) : BaseDto, HasId {
    override val itemId: String
        get() = promoId
}