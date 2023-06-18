package ru.toxyxd.home

import ru.toxyxd.yaapi.dto.catalog.CatalogBlockDto
import ru.toxyxd.yaapi.dto.catalog.CatalogBlockEntityDto
import ru.toxyxd.yaapi.dto.catalog.CatalogRootDto
import ru.toxyxd.yaapi.dto.recently.RecentlyDto

internal class YaCatalogBinder {
    fun transform(
        dto: CatalogRootDto,
    ): List<YaCatalogEntry> {
        return dto.blocks.map { block ->
            when (block.type) {
                CatalogBlockDto.Type.PersonalPlaylists,
                CatalogBlockDto.Type.PlayContexts,
                CatalogBlockDto.Type.Promotions -> sliderEntry(block)

                else -> YaCatalogEntry.Unknown(block.itemId, block.type.name, block.title)
            }
        }
    }

    private fun listToEntries(
        block: CatalogBlockDto
    ): List<YaCatalogEntry> {
        return block.entities.map { entity ->
            when (entity) {
                is CatalogBlockEntityDto.PersonalPlaylistBlockEntityDto -> YaCatalogEntry.PersonalizedPlaylist(entity.data.playlistHeader)
                is CatalogBlockEntityDto.PlayContextBlockEntityDto -> {
                    when (val data = entity.data) {
                        is RecentlyDto.RecentlyAlbumDto -> YaCatalogEntry.Recent.Album(data.album)
                        is RecentlyDto.RecentlyPlaylistDto -> YaCatalogEntry.Recent.Playlist(data.playlist)
                        else -> YaCatalogEntry.Unknown(entity.itemId, entity.type.name + ':' + entity.data.context, null)
                    }
                }
                is CatalogBlockEntityDto.PromotionBlockEntityDto -> YaCatalogEntry.Promotion(entity.data)
                else -> YaCatalogEntry.Unknown(block.itemId, block.type.name, block.title)
            }
        }
    }

    private fun sliderEntry(
        dto: CatalogBlockDto
    ) = YaCatalogEntry.Slider(
        itemId = dto.itemId,
        headerTitle = dto.title!!,
        sliderType = dto.type.asSliderType(),
        items = listToEntries(dto)
    )
}