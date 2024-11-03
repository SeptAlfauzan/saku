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
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

@Composable
fun TodayCard(
    modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    val today = CalendarDay(
        date = LocalDate.now(),
        position = DayPosition.MonthDate
    )
    val formatedDateMonth = "${today.date.dayOfMonth}/${today.date.month.number}"
    val year = today.date.year

    Card(backgroundColor = Color(0xFFBFFF67)) {
        Column(modifier.padding(12.dp)) {
            Text(
                "Today", style = textStyle.copy(fontSize = 24.sp)
            )
            Text(
                formatedDateMonth, style = textStyle.copy(
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                year.toString(), style = textStyle.copy(
                    fontSize = 64.sp,
                )
            )
        }
    }
}