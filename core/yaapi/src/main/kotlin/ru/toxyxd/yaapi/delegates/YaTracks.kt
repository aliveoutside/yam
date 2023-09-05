package ru.toxyxd.yaapi.delegates

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.track.TrackDownloadInfoDto
import ru.toxyxd.yaapi.dto.track.TrackMp3InfoDto
import ru.toxyxd.yaapi.internal.YaApiError
import ru.toxyxd.yaapi.internal.YaApiResponse

@JvmInline
value class YaTracks(
    val yaApi: YaApi
) {
    suspend fun getMp3UrlForTrack(trackId: String): YaApiResponse<String> {
        return when (val downloadInfoResponse = getDownloadInfo(trackId)) {
            is YaApiResponse.Success -> {
                val downloadInfo = downloadInfoResponse.result
                getMp3Url(downloadInfo)
            }

            is YaApiResponse.Error -> YaApiResponse.Error(downloadInfoResponse.error)
            is YaApiResponse.HttpError -> YaApiResponse.HttpError(
                downloadInfoResponse.code,
                downloadInfoResponse.description
            )

            is YaApiResponse.InternalError -> YaApiResponse.InternalError(downloadInfoResponse.exception)
        }
    }

    private suspend fun getDownloadInfo(trackId: String): YaApiResponse<List<TrackDownloadInfoDto>> =
        yaApi.api<List<TrackDownloadInfoDto>>(listOf("tracks", trackId, "download-info")) {}

    private suspend fun getMp3Url(downloadInfo: List<TrackDownloadInfoDto>): YaApiResponse<String> {
        val track = resolveHighestQuality(downloadInfo)
        return track.let { info ->
            val urlInfo: TrackMp3InfoDto? = yaApi.httpClient.get(info.downloadInfoUrl + "&format=json").body()
            val mp3Url = urlInfo?.let { "https://${it.host}/get-mp3/${it.s}/${it.ts}${it.path}" }
            mp3Url?.let { YaApiResponse.Success(it) } ?: YaApiResponse.Error(YaApiError("Failed to get MP3 URL", ""))
        }
    }

    private fun resolveHighestQuality(downloadInfo: List<TrackDownloadInfoDto>): TrackDownloadInfoDto {
        return downloadInfo.maxByOrNull { it.bitrateInKbps } ?: error("No download info available")
    }
}
