package org.kudos.saku.app.setting.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


data class SettingMenu(
    val text: String,
    val icon: ImageVector,
    val onTap: () -> Unit,
    val content: (@Composable () -> Unit)? = null,
    val actionWidget: (@Composable () -> Unit)? = null
)
