package ru.toxyxd.common

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.value.Value

fun <T : Any> Value<T>.asNavigationSource(): NavigationSource<T> = ValueAsNavigationSource(this)

private class ValueAsNavigationSource<T : Any>(
    private val parentState: Value<T>,
) : NavigationSource<T> {
    override fun subscribe(observer: (T) -> Unit) = parentState.subscribe(observer)
}