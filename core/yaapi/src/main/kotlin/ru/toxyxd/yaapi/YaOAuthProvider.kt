package ru.toxyxd.yaapi

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.AuthProvider
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.AuthScheme
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.util.KtorDsl
import kotlinx.coroutines.CompletableDeferred
import java.util.concurrent.atomic.AtomicReference


/*
* Basically this is a copy of io.ktor.client.plugins.auth.providers.BearerAuthProvider
* with some changes to make it work with Yandex OAuth
*
* The main problem is that Yandex uses bearer, but with OAuth prefix
 */

/**
 * Installs the client's [BearerAuthProvider].
 */
public fun Auth.yandexBearer(block: BearerAuthConfig.() -> Unit) {
    with(BearerAuthConfig().apply(block)) {
        this@yandexBearer.providers.add(
            BearerAuthProvider(
                _refreshTokens,
                _loadTokens,
                _sendWithoutRequest,
                realm
            )
        )
    }
}

public class BearerTokens(
    public val accessToken: String,
    public val refreshToken: String
)

/**
 * Parameters to be passed to [BearerAuthConfig.refreshTokens] lambda.
 */
public class RefreshTokensParams(
    public val client: HttpClient,
    public val response: HttpResponse,
    public val oldTokens: BearerTokens?
) {

    /**
     * Marks that this request is for refreshing auth tokens, resulting in a special handling of it.
     */
    public fun HttpRequestBuilder.markAsRefreshTokenRequest() {
        attributes.put(Auth.AuthCircuitBreaker, Unit)
    }
}

/**
 * A configuration for [BearerAuthProvider].
 */
@KtorDsl
public class BearerAuthConfig {
    internal var _refreshTokens: suspend RefreshTokensParams.() -> BearerTokens? = { null }
    internal var _loadTokens: suspend () -> BearerTokens? = { null }
    internal var _sendWithoutRequest: (HttpRequestBuilder) -> Boolean = { true }

    public var realm: String? = null

    /**
     * Configures a callback that refreshes a token when the 401 status code is received.
     */
    public fun refreshTokens(block: suspend RefreshTokensParams.() -> BearerTokens?) {
        _refreshTokens = block
    }

    /**
     * Configures a callback that loads a cached token from a local storage.
     * Note: Using the same client instance here to make a request will result in a deadlock.
     */
    public fun loadTokens(block: suspend () -> BearerTokens?) {
        _loadTokens = block
    }

    /**
     * Sends credentials without waiting for [HttpStatusCode.Unauthorized].
     */
    public fun sendWithoutRequest(block: (HttpRequestBuilder) -> Boolean) {
        _sendWithoutRequest = block
    }
}

/**
 * An authentication provider for the Bearer HTTP authentication scheme.
 * Bearer authentication involves security tokens called bearer tokens.
 * As an example, these tokens can be used as a part of OAuth flow to authorize users of your application
 * by using external providers, such as Google, Facebook, Twitter, and so on.
 *
 * You can learn more from [Bearer authentication](https://ktor.io/docs/bearer-client.html).
 */
class BearerAuthProvider(
    private val refreshTokens: suspend RefreshTokensParams.() -> BearerTokens?,
    loadTokens: suspend () -> BearerTokens?,
    private val sendWithoutRequestCallback: (HttpRequestBuilder) -> Boolean = { true },
    private val realm: String?
) : AuthProvider {

    @Suppress("OverridingDeprecatedMember")
    @Deprecated("Please use sendWithoutRequest function instead")
    override val sendWithoutRequest: Boolean
        get() = error("Deprecated")

    private val tokensHolder = AuthTokenHolder(loadTokens)

    override fun sendWithoutRequest(request: HttpRequestBuilder): Boolean =
        sendWithoutRequestCallback(request)

    /**
     * Checks if current provider is applicable to the request.
     */
    override fun isApplicable(auth: HttpAuthHeader): Boolean {
        if (auth.authScheme != AuthScheme.Bearer) {
            return false
        }
        val isSameRealm = when {
            realm == null -> true
            auth !is HttpAuthHeader.Parameterized -> false
            else -> auth.parameter("realm") == realm
        }

        return isSameRealm
    }

    /**
     * Adds an authentication method headers and credentials.
     */
    override suspend fun addRequestHeaders(
        request: HttpRequestBuilder,
        authHeader: HttpAuthHeader?
    ) {
        val token = tokensHolder.loadToken() ?: return

        request.headers {
            val tokenValue = "OAuth ${token.accessToken}"
            if (contains(HttpHeaders.Authorization)) {
                remove(HttpHeaders.Authorization)
            }
            append(HttpHeaders.Authorization, tokenValue)
        }
    }

    override suspend fun refreshToken(response: HttpResponse): Boolean {
        val newToken = tokensHolder.setToken {
            refreshTokens(
                RefreshTokensParams(
                    response.call.client,
                    response,
                    tokensHolder.loadToken()
                )
            )
        }
        return newToken != null
    }

    fun clearToken() {
        tokensHolder.clearToken()
    }
}

internal class AuthTokenHolder<T>(
    private val loadTokens: suspend () -> T?
) {
    private val refreshTokensDeferred = AtomicReference<CompletableDeferred<T?>?>(null)
    private val loadTokensDeferred = AtomicReference<CompletableDeferred<T?>?>(null)

    internal fun clearToken() {
        loadTokensDeferred.set(null)
        refreshTokensDeferred.set(null)
    }

    internal suspend fun loadToken(): T? {
        var deferred: CompletableDeferred<T?>?
        lateinit var newDeferred: CompletableDeferred<T?>
        while (true) {
            deferred = loadTokensDeferred.get()
            val newValue = deferred ?: CompletableDeferred()
            if (loadTokensDeferred.compareAndSet(deferred, newValue)) {
                newDeferred = newValue
                break
            }
        }

        // if there's already a pending loadTokens(), just wait for it to complete
        if (deferred != null) {
            return deferred.await()
        }

        try {
            val newTokens = loadTokens()

            // [loadTokensDeferred.value] could be null by now (if clearToken() was called while
            // suspended), which is why we are using [newDeferred] to complete the suspending callback.
            newDeferred.complete(newTokens)

            return newTokens
        } catch (cause: Throwable) {
            newDeferred.completeExceptionally(cause)
            loadTokensDeferred.compareAndSet(newDeferred, null)
            throw cause
        }
    }

    internal suspend fun setToken(block: suspend () -> T?): T? {
        var deferred: CompletableDeferred<T?>?
        lateinit var newDeferred: CompletableDeferred<T?>
        while (true) {
            deferred = refreshTokensDeferred.get()
            val newValue = deferred ?: CompletableDeferred()
            if (refreshTokensDeferred.compareAndSet(deferred, newValue)) {
                newDeferred = newValue
                break
            }
        }

        try {
            val newToken = if (deferred == null) {
                val newTokens = block()

                // [refreshTokensDeferred.value] could be null by now (if clearToken() was called while
                // suspended), which is why we are using [newDeferred] to complete the suspending callback.
                newDeferred.complete(newTokens)
                refreshTokensDeferred.set(null)
                newTokens
            } else {
                deferred.await()
            }
            loadTokensDeferred.set(CompletableDeferred(newToken))
            return newToken
        } catch (cause: Throwable) {
            newDeferred.completeExceptionally(cause)
            refreshTokensDeferred.compareAndSet(newDeferred, null)
            throw cause
        }
    }
}

