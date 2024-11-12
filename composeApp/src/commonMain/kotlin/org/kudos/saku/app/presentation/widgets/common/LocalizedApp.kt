package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import org.kudos.saku.utils.Language

val LocalLocalization = staticCompositionLocalOf { Language.Indonesia.isoFormat }


@Composable
fun LocalizedApp(language: String = Language.Indonesia.isoFormat, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLocalization provides language, content = content)
}