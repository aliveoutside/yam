package ru.toxyxd.home.component.common

import ru.toxyxd.common.HasId

interface PlaylistComponent : HasId {
    val title: String
    val subtitle: String?
    val cover: String
}