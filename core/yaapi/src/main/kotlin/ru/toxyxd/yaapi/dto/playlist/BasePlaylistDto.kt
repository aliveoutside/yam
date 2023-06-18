package ru.toxyxd.yaapi.dto.playlist

import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.cover.CoverInfoDto

interface BasePlaylistDto : HasId {
    val title: String
    val description: String?
    val cover: CoverInfoDto?
}
