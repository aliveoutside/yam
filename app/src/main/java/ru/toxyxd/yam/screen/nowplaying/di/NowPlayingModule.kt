package ru.toxyxd.yam.screen.nowplaying.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.toxyxd.yam.screen.nowplaying.MiniPlayerViewModel

val nowPlayingModule = module {
    viewModelOf(::MiniPlayerViewModel)
}