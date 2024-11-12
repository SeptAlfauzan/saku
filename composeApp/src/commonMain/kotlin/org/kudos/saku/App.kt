package org.kudos.saku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.kudos.saku.app.home.presentation.HomeScreen
import org.kudos.saku.app.presentation.viewmodels.CashFlowViewModel
import org.kudos.saku.app.presentation.widgets.common.LocalizedApp
import org.kudos.saku.app.setting.presentation.SettingViewModel
import org.kudos.saku.shared.Localization
import org.kudos.saku.utils.Language

@Composable
@Preview()
fun App() {

    MaterialTheme {
        KoinContext {
            val cashFlowViewModel = koinInject<CashFlowViewModel>()
            val settingViewModel = koinInject<SettingViewModel>()

            val lang by settingViewModel.currentLanguage.collectAsState(initial = Language.Indonesia)

            LaunchedEffect(Unit) {
                Localization().changeLang(lang.isoFormat)
            }

            LocalizedApp(lang.isoFormat) {

                LaunchedEffect(lang) {
                    Napier.d { "language from preference ${lang.isoFormat}" }
                    Localization().changeLang(lang.isoFormat)
                }

                Column(
                    Modifier.fillMaxSize().background(Color(0xFFF9F9F9)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Navigator(
                        HomeScreen(cashFlowViewModel, settingViewModel)
                    )
                }
            }
        }
    }
}