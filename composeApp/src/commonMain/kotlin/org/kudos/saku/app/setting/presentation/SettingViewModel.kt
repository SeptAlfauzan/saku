package org.kudos.saku.app.setting.presentation

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kudos.saku.shared.DataStorePlatform
import org.kudos.saku.utils.Language

class SettingViewModel(private val dataStorePlatform: DataStorePlatform) : ViewModel() {
    private val dataStore = dataStorePlatform.createPlatformDataStore()
    private val isDarkThemeKey = booleanPreferencesKey("isDarkTheme")
    private val languageKey = stringPreferencesKey("languageKey")

    private val _currentLanguage: MutableStateFlow<Language> = MutableStateFlow(Language.Indonesia)
    val currentLanguage: StateFlow<Language> = _currentLanguage

    private val _isDarkTheme: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        getCurrentLanguage()
    }

    fun setCurrentLanguage(language: Language){
        Napier.d { "Current language is changed!" }
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { it[languageKey] = language.isoFormat }
        }
    }

    fun getCurrentLanguage(){
        viewModelScope.launch {
            dataStore.data.map {
                Language.stringToLanguage(it[languageKey] ?: Language.Indonesia.isoFormat)
            }.collect{
                _currentLanguage.value = it
            }
        }
    }
}