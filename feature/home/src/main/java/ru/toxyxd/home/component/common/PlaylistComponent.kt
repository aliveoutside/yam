package ru.toxyxd.home.component.common

import ru.toxyxd.home.component.LandingComponent

interface PlaylistComponent : LandingComponent {
    val title: String
    val subtitle: String?
    val cover: String
}