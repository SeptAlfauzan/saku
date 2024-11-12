package org.kudos.saku.app.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kudos.saku.app.domain.repositories.SettingRepository
import org.kudos.saku.shared.DataStorePlatform
import org.kudos.saku.utils.Language

class SettingRepositoryImpl(override val dataStorePlatform: DataStorePlatform) : SettingRepository {
    private val dataStore = dataStorePlatform.createPlatformDataStore()
    private val isDarkThemeKey = booleanPreferencesKey("isDarkTheme")
    private val languageKey = stringPreferencesKey("languageKey")

    override fun isDarkTheme(): Flow<Boolean> = dataStore.data.map {
        it[isDarkThemeKey] ?: false
    }

    override suspend fun setDarkTheme(value: Boolean) {
        dataStore.edit {
            it[isDarkThemeKey] = value
        }
    }

    override fun getLanguageIsoFormat(): Flow<Language> = dataStore.data.map {
        Language.stringToLanguage(it[languageKey] ?: Language.English.isoFormat)
    }

    override suspend fun setLanguageIsoFormat(language: Language) {
        dataStore.edit { it[languageKey] = language.isoFormat }
    }
}