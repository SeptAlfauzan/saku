package org.kudos.saku.app.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.kudos.saku.shared.DataStorePlatform
import org.kudos.saku.utils.Language

interface SettingRepository {
    val dataStorePlatform: DataStorePlatform
    fun isDarkTheme(): Flow<Boolean>
    suspend fun setDarkTheme(value: Boolean)
    fun getLanguageIsoFormat() : Flow<Language>
    suspend fun setLanguageIsoFormat(language: Language)
}