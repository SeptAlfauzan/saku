package org.kudos.saku.app.presentation.widgets.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TodayCard(modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    Card(backgroundColor = Color(0xFFBFFF67)) {

        Column(modifier.padding(12.dp)) {
            Text(
                "Today", style = textStyle
            )
            Text(
                "19/10", style = textStyle.copy(
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                "2024", style = textStyle.copy(
                    fontSize = 64.sp,
                )
            )
        }
    }
}