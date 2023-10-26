package ru.toxyxd.yaapi.delegates

import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.YaApiConstants
import ru.toxyxd.yaapi.dto.auth.RequestOAuthResponse

@JvmInline
value class YaAuthentication(private val api: YaApi) {
    fun generateAuthUrl(username: String? = null): String {
        return "https://${YaApiConstants.OAuthUrl}/authorize?response_type=code&client_id=${YaApiConstants.clientId}&login_hint=$username"
    }

    suspend fun refreshToken(refreshToken: String) =
        api.oauth<RequestOAuthResponse>(listOf("token")) {
            param("grant_type", "refresh_token")
            param("refresh_token", refreshToken)
            param("client_id", YaApiConstants.clientId)
            param("client_secret", YaApiConstants.clientSecret)
        }
}