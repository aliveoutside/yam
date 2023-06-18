package ru.toxyxd.common

import kotlinx.serialization.Transient

interface HasId {
    @Transient
    val itemId: String
}