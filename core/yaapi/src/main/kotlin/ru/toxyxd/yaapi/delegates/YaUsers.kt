package ru.toxyxd.yaapi.delegates

import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.dto.user.YaUser

class YaUsers(private val yaApi: YaApi) {
    suspend fun getMe(token: String) = yaApi.login<YaUser>(listOf("info")) {
        param("oauth_token", token)
        param("format", "json")
    }
}