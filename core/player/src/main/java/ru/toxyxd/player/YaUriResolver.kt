package ru.toxyxd.player

import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.ResolvingDataSource
import kotlinx.coroutines.runBlocking
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.internal.YaApiResponse

class YaUriResolver(private val yaApi: YaApi) {
    suspend fun resolve(id: String): String {
        return when (val info = yaApi.tracks.getMp3UrlForTrack(id)) {
            is YaApiResponse.Success -> info.result
            is YaApiResponse.InternalError -> throw info.exception
            is YaApiResponse.HttpError -> throw Exception(info.description)
            is YaApiResponse.Error -> throw Exception(info.error.error)
        }
    }
}

@UnstableApi
class YaDataSpecResolver(private val uriResolver: YaUriResolver) : ResolvingDataSource.Resolver {
    override fun resolveDataSpec(dataSpec: DataSpec): DataSpec {
        return dataSpec.withUri(
            Uri.parse(
                runBlocking {
                    uriResolver.resolve(
                        dataSpec.uri.toString()
                    )
                }
            )
        )
    }
}