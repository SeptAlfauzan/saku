package org.kudos.saku.app.presentation.widgets.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.kudos.saku.app.presentation.widgets.common.PillButton


@Composable
fun SeeChartCard(modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    Card(backgroundColor = Color(0xFFFFCC67)) {
        Column(modifier.padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text("Tap button bellow to see your financial flow report", style = textStyle)
            PillButton("see report", onClick = {}, radiusCorner = 32.dp)
        }
    }
}
