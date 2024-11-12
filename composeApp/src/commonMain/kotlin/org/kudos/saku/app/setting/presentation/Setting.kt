package org.kudos.saku.app.setting.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.kudos.saku.app.presentation.widgets.common.TopBarWithBackButton
import org.kudos.saku.app.setting.domain.SettingMenu
import org.kudos.saku.app.setting.presentation.widgets.SettingMenuContainer
import org.kudos.saku.app.setting.presentation.widgets.SettingMenuItem
import org.kudos.saku.shared.Localization
import org.kudos.saku.utils.Language
import saku.composeapp.generated.resources.Res
import saku.composeapp.generated.resources.export_to_csv
import saku.composeapp.generated.resources.setting
import saku.composeapp.generated.resources.theme

class SettingScreen(
    private val setCurrentLanguage: (Language) -> Unit,
    private val currentLanguage: StateFlow<Language>
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val lang = currentLanguage.collectAsState().value

        SettingView(
            currentLanguage = lang,
            setCurrentLanguage = {
                setCurrentLanguage(it)
                Localization().changeLang(it.isoFormat)
            },
            navigateBack = { navigator.pop() })
    }
}

@Composable
fun SettingView(
    currentLanguage: Language,
    setCurrentLanguage: (Language) -> Unit,
    navigateBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    val menu = listOf(
        SettingMenu(
            text = stringResource(Res.string.theme),
            icon = Icons.Default.Star,
            onTap = {},
            actionWidget = {
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Black
                    )
                )
            }
        ),
        SettingMenu(
            text = stringResource(Res.string.export_to_csv),
            icon = Icons.Default.Star,
            onTap = {},
        ),
    )

    Scaffold(topBar = { TopBarWithBackButton(navigateBack) }) {
        Column(Modifier.padding(24.dp)) {
            Text(
                stringResource(Res.string.setting),
                style = textStyle.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(menu) { SettingMenuItem(it, modifier = Modifier.fillMaxWidth()) }
                item {
                    DropdownChangeLanguage(currentLanguage = currentLanguage) { language ->
                        coroutineScope.launch {
                            setCurrentLanguage(language)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun DropdownChangeLanguage(
    currentLanguage: Language,
    onChange: (Language) -> Unit
) {
    val options = listOf(Language.Indonesia, Language.English, Language.Turkish)
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(currentLanguage.name) }

    SettingMenuContainer(modifier = Modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp))
                .clickable { expanded = !expanded },
        ) {
            Text(
                text = selectedOptionText,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Icon(
                if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                "contentDescription",
                Modifier.align(Alignment.CenterEnd)
            )
            DropdownMenu(
                modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier,
                        onClick = {
                            selectedOptionText = selectionOption.name
                            expanded = false
                            onChange(selectionOption)
                        }
                    ) {
                        Text(text = selectionOption.name)
                    }
                }
            }
        }
    }
}
