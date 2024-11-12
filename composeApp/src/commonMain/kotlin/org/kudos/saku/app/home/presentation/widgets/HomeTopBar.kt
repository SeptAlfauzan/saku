package org.kudos.saku.app.home.presentation.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.StateFlow
import org.kudos.saku.app.setting.presentation.SettingScreen
import org.kudos.saku.utils.Language

@Composable
fun HomeTopBar(
    currentLanguage: StateFlow<Language>,
    setCurrentLanguage: (Language) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        actions = {
            IconButton(
                onClick = { navigator.push(SettingScreen(setCurrentLanguage, currentLanguage)) },
            ) {
                Icon(Icons.Default.Settings, "more")
            }

        },
        title = {})
}