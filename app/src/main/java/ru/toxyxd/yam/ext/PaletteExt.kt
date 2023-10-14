package ru.toxyxd.yam.ext

import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch

private fun Palette.getMostPopulousSwatch() : Swatch {
    var mostPopulous: Swatch? = null
    for (swatch in this.swatches) {
        if (mostPopulous == null || swatch.population > mostPopulous.population) {
            mostPopulous = swatch
        }
    }

    return mostPopulous!!
}

fun Palette.getSurfaceColor() : Int {
    val hsl = FloatArray(3)

    if (this.vibrantSwatch != null) {
        hsl[0] = this.vibrantSwatch!!.hsl[0]
        hsl[1] = this.vibrantSwatch!!.hsl[1]
        hsl[2] = 0.45f
    } else if (this.dominantSwatch != null) {
        hsl[0] = this.dominantSwatch!!.hsl[0]
        hsl[1] = this.dominantSwatch!!.hsl[1]
        hsl[2] = 0.2f
    } else {
        val swatch = this.getMostPopulousSwatch()
        hsl[0] = swatch.hsl[0]
        hsl[1] = swatch.hsl[1]
        hsl[2] = 0.4f
    }

    return android.graphics.Color.HSVToColor(hsl)
}