package ru.toxyxd.home.component.common

import ru.toxyxd.home.component.LandingComponent

interface ArtistComponent : LandingComponent {
    val name: String
    val image: String

    fun onClick()
}