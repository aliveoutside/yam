package ru.toxyxd.home.component.common

import ru.toxyxd.home.component.LandingComponent

interface AlbumComponent : LandingComponent {
    val title: String
    val artist: String
    val type: String?
    val cover: String
}