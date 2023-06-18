package ru.toxyxd.home

import com.arkivanov.decompose.value.Value
import ru.toxyxd.common.HasIdComponent

interface HomeComponent {
    val children: Value<List<HasIdComponent>>
}