package org.kudos.saku.app.home.presentation.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.now
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.jetbrains.compose.resources.stringResource
import org.kudos.saku.app.presentation.widgets.common.PillButton
import org.kudos.saku.utils.UIState
import org.kudos.saku.utils.toIdr
import saku.composeapp.generated.resources.Res
import saku.composeapp.generated.resources.detail_report
import saku.composeapp.generated.resources.month_cash_different
import saku.composeapp.generated.resources.today
import saku.composeapp.generated.resources.today_cash_different

@Composable
fun TodayCard(
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
    val formatedDateMonth = "${today.date.dayOfMonth}/${today.date.month.number}"
    val year = today.date.year

    LaunchedEffect(Unit) {
        loadTodayAndMonthCashFlowReport(today.date.toString())
    }

    Card(backgroundColor = Color(0xFFBFFF67), shape = RoundedCornerShape(12.dp)) {
        Column(modifier.padding(12.dp)) {
            Text(
                stringResource(Res.string.today), style = textStyle.copy(fontSize = 24.sp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Text(
                    formatedDateMonth, style = textStyle.copy(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    year.toString(), style = textStyle.copy(
                        fontSize = 48.sp,
                    )
                )
            }
            Box(
                Modifier.padding(vertical = 16.dp).fillMaxWidth().height(2.dp)
                    .background(Color.White).clip(
                        RoundedCornerShape(4.dp)
                    )
            )
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
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                stringResource(Res.string.today_cash_different),
                                style = textStyle
                            )
                            Row {
                                if (it.data.first != 0L) {
                                    Icon(
                                        Icons.AutoMirrored.Default.ArrowForward,
                                        modifier = Modifier.rotate(if (it.data.first > 0L) -45f else 45f),
                                        contentDescription = null,
                                        tint = Color(if (it.data.first > 0L) 0xFF29515B else 0xFFFF4D4D)
                                    )
                                }
                                Text(
                                    it.data.first.toIdr(),
                                    style = textStyle.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        fontSize = 24.sp
                                    )
                                )
                            }
                            Text(
                                stringResource(Res.string.month_cash_different),
                                style = textStyle,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                            Row {
                                if (it.data.second != 0L) {
                                    Icon(
                                        Icons.AutoMirrored.Default.ArrowForward,
                                        modifier = Modifier.rotate(if (it.data.second > 0L) -45f else 45f),
                                        contentDescription = null,
                                        tint = Color(if (it.data.second > 0L) 0xFF29515B else 0xFFFF4D4D)
                                    )
                                }
                                Text(
                                    it.data.second.toIdr(),
                                    style = textStyle.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        fontSize = 24.sp
                                    )
                                )
                            }
                        }
                        PillButton(
                            stringResource(Res.string.detail_report),
                            onClick = navigateStatsView,
                            radiusCorner = 32.dp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    }
}