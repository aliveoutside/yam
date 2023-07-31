package ru.toxyxd.yam.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.value.Value
import ru.toxyxd.common.util.Optional

@Composable
fun <T : Any> Value<Optional<T>>.subscribeAsState(): State<T?> {
    val state = remember(this) { mutableStateOf(value.value) }

    DisposableEffect(this) {
        val observer: (Optional<T>) -> Unit = { state.value = it.value }

        subscribe(observer)

        onDispose {
            unsubscribe(observer)
        }
    }

    return state
}