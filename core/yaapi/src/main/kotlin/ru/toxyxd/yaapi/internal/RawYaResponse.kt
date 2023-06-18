package ru.toxyxd.yaapi.internal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawYaResponse<T>(
    val result: T? = null,
    val error: YaApiError? = null,
)

@Serializable
data class YaApiError(
    @SerialName("error") val error: String,
    @SerialName("error_description") val errorDescription: String,
)

sealed class YaApiResponse<T> {
    data class Success<T>(val result: T) : YaApiResponse<T>()
    data class Error<T>(val error: YaApiError) : YaApiResponse<T>()
    data class HttpError<T>(val code: Int, val description: String) : YaApiResponse<T>()
    data class InternalError<T>(val exception: Throwable) : YaApiResponse<T>()
}

sealed class YaOAuthResponse<T> {
    data class Success<T>(val result: T) : YaOAuthResponse<T>()
    data class Error<T>(val error: Throwable) : YaOAuthResponse<T>()
}