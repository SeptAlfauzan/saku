package org.kudos.saku.shared

import java.util.Locale

actual class Localization {
    actual fun changeLang(langId: String) {
        val locale = Locale(langId)
        Locale.setDefault(locale)
    }
}