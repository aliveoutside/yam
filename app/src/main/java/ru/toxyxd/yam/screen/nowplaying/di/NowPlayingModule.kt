package ru.toxyxd.yam.screen.nowplaying.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.toxyxd.yam.screen.nowplaying.NowPlayingViewModel

val nowPlayingModule = module {
    viewModelOf(::NowPlayingViewModel)
}