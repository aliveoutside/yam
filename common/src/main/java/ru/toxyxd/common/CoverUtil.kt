package ru.toxyxd.common

object CoverUtil {
    fun getSmallCover(cover: String) = "https://" + cover.replace("%%", "100x100")
    fun getMediumCover(cover: String) = "https://" + cover.replace("%%", "400x400")
    fun getLargeCover(cover: String) = "https://" + cover.replace("%%", "800x800")
}