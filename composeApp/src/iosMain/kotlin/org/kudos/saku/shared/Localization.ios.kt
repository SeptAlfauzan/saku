package org.kudos.saku.shared

actual class Localization {
    actual fun changeLang(langId: String) {
        NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang),”AppleLanguages”)
    }
}