package ru.toxyxd.home.component.promotion

import ru.toxyxd.common.HasId

interface PromotionComponent: HasId {
    val heading: String
    val title: String
    val subtitle: String

    val imageUrl: String
}