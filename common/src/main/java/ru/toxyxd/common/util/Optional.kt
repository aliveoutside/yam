package ru.toxyxd.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.value.Value

// https://github.com/arkivanov/Decompose/discussions/55
data class Optional<T : Any>(val value: T?)