package ru.toxyxd.yam

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.toxyxd.player.di.playerModule
import ru.toxyxd.yam.di.mainModule
import ru.toxyxd.yam.screen.nowplaying.di.nowPlayingModule

class YamApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)

            modules(mainModule, playerModule, nowPlayingModule)
        }
    }
}