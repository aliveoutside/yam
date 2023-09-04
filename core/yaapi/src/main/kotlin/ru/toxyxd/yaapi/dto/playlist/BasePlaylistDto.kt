package ru.toxyxd.yaapi.dto.playlist

import ru.toxyxd.common.HasId
import ru.toxyxd.yaapi.dto.BaseDto
import ru.toxyxd.yaapi.dto.cover.CoverInfoDto

interface BasePlaylistDto : BaseDto, HasId {
    val uid: String
    val kind: String

    val title: String
    val description: String?
    val cover: CoverInfoDto?
}
