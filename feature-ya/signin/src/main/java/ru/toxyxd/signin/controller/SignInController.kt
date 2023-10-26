package ru.toxyxd.signin.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.account.YaAccount
import ru.toxyxd.yaapi.internal.YaApiResponse

class SignInController(
    private val yaApi: YaApi,
    private val onSuccess: (YaAccount) -> Unit,
    private val onError: (String) -> Unit
) {
    suspend fun signIn(
        token: String,
        expiresIn: Long
    ) = withContext(Dispatchers.Default) {
        when (val response = withContext(Dispatchers.Default) {
            yaApi.users.getMe(token)
        }) {
            is YaApiResponse.Success -> {
                YaAccount(
                    id = response.result.id,
                    accessToken = token,
                    expiresIn = expiresIn,
                ).let {
                    withContext(Dispatchers.Main) { onSuccess(it) }
                }
            }

            is YaApiResponse.Error -> onError(response.error.error)
            is YaApiResponse.HttpError -> onError(response.code.toString() + ": " + response.description)
            is YaApiResponse.InternalError -> onError(response.exception.message ?: "Unknown error")
        }
    }
}