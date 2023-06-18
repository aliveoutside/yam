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
    suspend fun dispatchSignIn(
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
        when (val user = withContext(Dispatchers.Default) {
            yaApi.users.getMe(result.accessToken)
        }) {
            is YaApiResponse.Success -> {
                YaAccount(
                    id = user.result.id,
                    accessToken = result.accessToken,
                    expiresIn = result.expiresIn,
                    refreshToken = result.refreshToken,
                ).let(onSuccess)
            }

            is YaApiResponse.Error -> onError(user.error.error)
            is YaApiResponse.HttpError -> onError(user.code.toString() + ": " + user.description)
            is YaApiResponse.InternalError -> onError(user.exception.message ?: "Unknown error")
        }

}