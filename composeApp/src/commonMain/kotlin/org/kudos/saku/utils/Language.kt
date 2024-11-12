package org.kudos.saku.utils

sealed class Language(val isoFormat: String, val name: String) {
    data object English : Language("en", "English")
    data object Indonesia : Language("id", "Bahasa")
    data object Turkish : Language("tr", "Turkish")

    companion object {
        fun stringToLanguage(languageString: String): Language {
            return when (languageString) {
                Language.English.isoFormat -> Language.English
                Language.Indonesia.isoFormat -> Language.Indonesia
                Language.Turkish.isoFormat -> Language.Turkish
                else -> Language.English
            }
        }
    }
}
