package org.kudos.saku.app.presentation.widgets.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.now
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import org.kudos.saku.app.presentation.widgets.common.PillButton
import org.kudos.saku.utils.UIState
import org.kudos.saku.utils.toIdr


@Composable
fun StatsCard(
    todayAndMonthCashFlowReport: StateFlow<UIState<Pair<Long, Long>>>,
    loadTodayAndMonthCashFlowReport: (date: String) -> Unit,
    navigateStatsView: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    val today = CalendarDay(
        date = LocalDate.now(),
        position = DayPosition.MonthDate
    )
    LaunchedEffect(Unit) {
        loadTodayAndMonthCashFlowReport(today.date.toString())
    }
    Card(backgroundColor = Color(0xFFFFCC67)) {
        todayAndMonthCashFlowReport.collectAsState().value.let {
            when (it) {
                is UIState.Error -> Text(
                    "Error: ${it.error}",
                    modifier.padding(12.dp),
                    style = textStyle
                )

                is UIState.Loading -> CircularProgressIndicator()
                is UIState.Success -> Column(
                    modifier.padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Today:",
                            style = textStyle)
                        Text(it.data.first.toIdr(),
                            style = textStyle.copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Right))
                        Text(
                            "Month:",
                            style = textStyle,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        Text(it.data.second.toIdr(),
                            style = textStyle.copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Right))
                    }
                    PillButton(
                        "detail report",
                        onClick = navigateStatsView,
                        radiusCorner = 32.dp
                    )
                }
            }
        }

    }
}
