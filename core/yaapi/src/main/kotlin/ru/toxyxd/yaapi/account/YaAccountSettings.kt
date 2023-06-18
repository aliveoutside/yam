@file:OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)

package ru.toxyxd.yaapi.account

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@JvmInline
value class YaAccountSettings(
    private val settings: Settings
) {
    fun getAccount(): YaAccount? {
        return settings[keyDef, 0].takeIf { accountId ->
            accountId != 0
        }?.let { accountId ->
            settings.decodeValueOrNull(YaAccount.serializer(), keyAccount(accountId))
        }
    }

    fun saveAccount(newAccount: YaAccount) {
        require(newAccount.id != 0) { "Account should not be empty in order to be saved!" }

        settings.encodeValue(YaAccount.serializer(), keyAccount(newAccount.id), newAccount)

        if (settings[keyDef, 0] == 0) {
            settings.putInt(keyDef, newAccount.id)
        }
    }

    private val keyDef get() = "ya.defaultAccount"
    private fun keyAccount(id: Int) = "ya.accounts.${id}"
}