package org.kudos.saku.app.setting.presentation.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.kudos.saku.app.setting.domain.SettingMenu

@Composable
fun SettingMenuItem(data: SettingMenu, modifier: Modifier = Modifier) {
    SettingMenuContainer(modifier) {
        Row(modifier = Modifier.padding(8.dp)) {

//        Icon(data.icon, null)
        if (data.content != null) {
            data.content
        } else {
            Text(data.text)
            Spacer(Modifier.weight(1f))
            if (data.actionWidget != null) data.actionWidget.invoke() else Icon(
                Icons.Default.KeyboardArrowRight,
                null
            )
        }
        }
    }
}

@Composable
fun SettingMenuContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) {
    val borderRadius = 12.dp
    Row(
        modifier
            .clip(RoundedCornerShape(borderRadius))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(borderRadius)
            ).height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
    }
}