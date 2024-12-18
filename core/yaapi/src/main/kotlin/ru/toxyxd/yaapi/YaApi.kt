package ru.toxyxd.yaapi

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.toxyxd.yaapi.account.YaAccount
import ru.toxyxd.yaapi.account.YaAccountSettings
import ru.toxyxd.yaapi.delegates.YaAlbums
import ru.toxyxd.yaapi.delegates.YaArtists
import ru.toxyxd.yaapi.delegates.YaAuthentication
import ru.toxyxd.yaapi.delegates.YaLanding
import ru.toxyxd.yaapi.delegates.YaPlaylists
import ru.toxyxd.yaapi.delegates.YaTracks
import ru.toxyxd.yaapi.delegates.YaUsers
import ru.toxyxd.yaapi.internal.RawYaResponse
import ru.toxyxd.yaapi.internal.YaApiResponse
import ru.toxyxd.yaapi.internal.YaOAuthResponse

class YaApi(
    baseHttpClient: HttpClient, settings: Settings
) {
    private val yaSettings = YaAccountSettings(settings)
    private var currentAccount: YaAccount? = null

    val albums = YaAlbums(this)
    val artists = YaArtists(this)
    val authentication = YaAuthentication(this)
    val landing = YaLanding(this)
    val tracks = YaTracks(this)
    val playlists = YaPlaylists(this)
    val users = YaUsers(this)

    val isAuthorized get() = currentAccount != null

    init {
        currentAccount = yaSettings.getAccount()
    }

    fun saveNewAccount(yaAccount: YaAccount) {
        yaSettings.saveAccount(yaAccount)
        currentAccount = yaAccount
        reloadAuthTokens(httpClient)
    }

    var httpClient = baseHttpClient.config {
        install(HttpCache)
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                isLenient = true
                prettyPrint = false
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        install(HttpCache)
        install(Auth) {
            yandexBearer {
                loadTokens {
                    BearerTokens(
                        currentAccount!!.accessToken,
                    )
                }
            }
        }
    }

    private fun reloadAuthTokens(client: HttpClient) {
        client.authProvider<YandexBearerAuthProvider>()?.clearToken()
    }

    suspend inline fun <reified T> oauth(
        path: List<String>, noinline scope: YaApiMethodScope.() -> Unit
    ) = try {
        executePost(YaApiConstants.OAuthUrl, path, scope = scope).body<T>().let {
            YaOAuthResponse.Success(it)
        }
    } catch (t: Throwable) {
        YaOAuthResponse.Error(t)
    }

    suspend inline fun <reified T> api(
        path: List<String>, noinline scope: YaApiMethodScope.() -> Unit
    ) = try {
        execute(YaApiConstants.ApiUrl, path, scope).unbox<T>()
    } catch (t: Throwable) {
        YaApiResponse.InternalError(t)
    }

    suspend inline fun <reified T> login(
        path: List<String>, noinline scope: YaApiMethodScope.() -> Unit
    ) = try {
        execute(YaApiConstants.LoginUrl, path, scope).body<T>().let {
            YaApiResponse.Success(it)
        }
    } catch (t: Throwable) {
        YaApiResponse.InternalError(t)
    }

    suspend inline fun <reified T> HttpResponse.unbox(): YaApiResponse<T> = try {
        when (status) {
            HttpStatusCode.OK -> {
                body<RawYaResponse<T>>().let { raw ->
                    if (raw.error != null) {
                        YaApiResponse.Error(raw.error)
                    } else {
                        YaApiResponse.Success(
                            raw.result ?: error("No result in response, but error is null")
                        )
                    }
                }
            }

            else -> {
                YaApiResponse.HttpError(status.value, status.description)
            }
        }
    } catch (t: Throwable) {
        YaApiResponse.InternalError(t)
    }

    suspend fun execute(
        url: String, path: List<String>, scope: YaApiMethodScope.() -> Unit
    ): HttpResponse {
        return httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = url
                appendPathSegments(path)

                scope(YaApiMethodScope(parameters))
            }
        }
    }

    suspend fun executePost(
        url: String, path: List<String>, scope: YaApiMethodScope.() -> Unit
    ): HttpResponse {
        val parametersBuilder = ParametersBuilder()
        scope(YaApiMethodScope(parametersBuilder))

        return httpClient.submitForm(
            formParameters = parametersBuilder.build()
        ) {
            url {
                protocol = URLProtocol.HTTPS
                host = url
                appendPathSegments(path)

                headers {
                    append("x-yandex-music-client", "YandexMusicAndroid/24023441")
                }
            }
        }
    }

    @JvmInline
    value class YaApiMethodScope(
        private val parameters: ParametersBuilder,
    ) {
        fun param(key: String, value: String) {
            parameters.append(key, value)
        }
    }
}
