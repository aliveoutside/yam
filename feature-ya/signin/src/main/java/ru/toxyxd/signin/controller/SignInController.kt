package ru.toxyxd.signin.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.toxyxd.yaapi.YaApi
import ru.toxyxd.yaapi.account.YaAccount
import ru.toxyxd.yaapi.dto.auth.RequestOAuthResponse
import ru.toxyxd.yaapi.internal.YaApiResponse
import ru.toxyxd.yaapi.internal.YaOAuthResponse

class SignInController(
    private val yaApi: YaApi,
    private val onSuccess: (YaAccount) -> Unit,
    private val onError: (String) -> Unit
) {
    suspend fun signIn(
        code: String
    ) = withContext(Dispatchers.Default) {
        yaApi.authentication.oauth(code).let { response ->
            withContext(Dispatchers.Main) {
                when (response) {
                    is YaOAuthResponse.Success -> {
                        processSuccessResponse(response.result)
                    }

                    is YaOAuthResponse.Error -> {
                        onError(response.error.message!!)
                    }
                }
            }
        }
    }

    private suspend fun processSuccessResponse(result: RequestOAuthResponse) =
        when (val response = withContext(Dispatchers.Default) {
            yaApi.users.getMe(result.accessToken)
        }) {
            is YaApiResponse.Success -> {
                YaAccount(
                    id = response.result.id,
                    accessToken = result.accessToken,
                    expiresIn = result.expiresIn,
                    refreshToken = result.refreshToken,
                ).let(onSuccess)
            }

            is YaApiResponse.Error -> onError(response.error.error)
            is YaApiResponse.HttpError -> onError(response.code.toString() + ": " + response.description)
            is YaApiResponse.InternalError -> onError(response.exception.message ?: "Unknown error")
        }
}