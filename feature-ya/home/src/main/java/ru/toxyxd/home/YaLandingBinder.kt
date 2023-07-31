package ru.toxyxd.home

import ru.toxyxd.yaapi.dto.landing.LandingBlockDto
import ru.toxyxd.yaapi.dto.landing.LandingBlockEntityDto
import ru.toxyxd.yaapi.dto.landing.LandingRootDto
import ru.toxyxd.yaapi.dto.recently.RecentlyDto

internal class YaLandingBinder {
    fun transform(
        dto: LandingRootDto,
    ): List<YaLandingEntry> {
        return dto.blocks.map { block ->
            when (block.type) {
                LandingBlockDto.Type.NewReleases,
                LandingBlockDto.Type.PersonalPlaylists,
                LandingBlockDto.Type.PlayContexts,
                LandingBlockDto.Type.Promotions -> sliderEntry(block)

                else -> YaLandingEntry.Unknown(block.itemId, block.type.name, block.title)
            }
        }
    }

    private fun listToEntries(
        block: LandingBlockDto
    ): List<YaLandingEntry> {
        return block.entities.map { entity ->
            when (entity) {
                is LandingBlockEntityDto.AlbumBlockEntityDto -> YaLandingEntry.Album(entity.data)
                is LandingBlockEntityDto.PersonalPlaylistBlockEntityDto -> YaLandingEntry.PersonalizedPlaylist(
                    entity.data.playlistHeader
                )

                is LandingBlockEntityDto.PlayContextBlockEntityDto -> {
                    when (val data = entity.data) {
                        is RecentlyDto.RecentlyAlbumDto -> YaLandingEntry.Recent.Album(data.album)
                        is RecentlyDto.RecentlyArtistDto -> YaLandingEntry.Recent.Artist(data.artist)
                        is RecentlyDto.RecentlyPlaylistDto -> YaLandingEntry.Recent.Playlist(data.playlist)
                        else -> YaLandingEntry.Unknown(
                            entity.itemId,
                            entity.type.name + ':' + entity.data.context,
                            null
                        )
                    }
                }

                is LandingBlockEntityDto.PromotionBlockEntityDto -> YaLandingEntry.Promotion(entity.data)
                else -> YaLandingEntry.Unknown(block.itemId, block.type.name, block.title)
            }
        }
    }

    private fun sliderEntry(
        dto: LandingBlockDto
    ) = YaLandingEntry.Slider(
        itemId = dto.itemId,
        headerTitle = dto.title!!,
        sliderType = dto.type.toSliderType(),
        items = listToEntries(dto)
    )
}