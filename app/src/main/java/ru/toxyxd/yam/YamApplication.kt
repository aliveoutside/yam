package ru.toxyxd.yam

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.toxyxd.yam.di.mainModule

class YamApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)

            modules(mainModule)
        }
    }
}