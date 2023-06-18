package ru.toxyxd.yam.di

import android.app.Application
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.toxyxd.yaapi.YaApi

val mainModule = module {
    single {
        HttpClient(OkHttp)
    }

    single {
        YaApi(get(), get())
    }

    single<Settings>(createdAtStart = true) {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences(
                "Yam",
                Application.MODE_PRIVATE
            )
        )
    }
}