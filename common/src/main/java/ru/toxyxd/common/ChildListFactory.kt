package ru.toxyxd.common

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.ParcelableContainer

fun <C : Any, T : ComponentContext> ComponentContext.childList(
    state: Value<List<C>>,
    key: String = "DefaultChildList",
    childFactory: (configuration: C, ComponentContext) -> T,
): Value<List<T>> = children(
    source = state.asNavigationSource(),
    key = key,
    initialState = { ListNavState(configurations = state.value) },
    saveState = {
        ParcelableContainer()
    },
    restoreState = { ListNavState(state.value) },
    navTransformer = { _, event -> ListNavState(configurations = event) },
    stateMapper = { _, children ->
        @Suppress("UNCHECKED_CAST")
        children as List<Child.Created<C, T>>
    },
    childFactory = childFactory,
).map { childList -> childList.map { it.instance } }

private data class ListNavState<out C : Any>(val configurations: List<C>) : NavState<C> {
    override val children: List<SimpleChildNavState<C>> = configurations.map { configuration ->
        SimpleChildNavState(
            configuration = configuration,
            status = ChildNavState.Status.ACTIVE,
        )
    }
}