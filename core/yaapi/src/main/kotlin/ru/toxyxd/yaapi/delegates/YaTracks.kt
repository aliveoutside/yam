package ru.toxyxd.yaapi.delegates

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.track.TrackDownloadInfoDto
import ru.toxyxd.yaapi.dto.track.TrackMp3InfoDto

@JvmInline
value class YaTracks(
    val yaApi: YaApi
) {
    suspend fun getDownloadInfo(trackId: String) =
        yaApi.api<List<TrackDownloadInfoDto>>(listOf("tracks", trackId, "download-info")) {}

    suspend fun getMp3Url(downloadInfo: TrackDownloadInfoDto): String {
        val urlInfo: TrackMp3InfoDto = yaApi.httpClient.get(downloadInfo.downloadInfoUrl!! + "&format=json").body()
        return "https://${urlInfo.host}/get-mp3/${urlInfo.s}/${urlInfo.ts}${urlInfo.path}"
    }
}