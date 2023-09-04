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
        val uri = when (val info = yaApi.tracks.getDownloadInfo(id)) {
            is YaApiResponse.Success -> yaApi.tracks.getMp3Url(info.result[0])
            else -> TODO()
        }
        return uri
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