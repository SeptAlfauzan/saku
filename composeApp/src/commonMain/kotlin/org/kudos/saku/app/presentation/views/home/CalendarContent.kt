package org.kudos.saku.app.presentation.views.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.YearMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.plusMonths
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.presentation.widgets.calendar.SimpleCalendarTitle
import org.kudos.saku.app.presentation.widgets.common.GroupedCashFlowCard
import org.kudos.saku.utils.advancedTitleCase

@Composable
fun CalendarContent(adjacentMonths: Int = 500) {

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
    var selectedDay by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    val coroutineScope = rememberCoroutineScope()
    val calendarContainerModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(Color.White)
        .border(
            BorderStroke(width = 1.dp, color = Color(0xFFA6A6A6)),
            shape = RoundedCornerShape(12.dp)
        )

    Column {
        Column(
            modifier = calendarContainerModifier,
        ) {
            SimpleCalendarTitle(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                currentMonth = state.firstVisibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1))
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1))
                    }
                },
            )
            HorizontalCalendar(
                modifier = Modifier.testTag("Calendar"),
                state = state,
                dayContent = { day ->
                    val isFromCurrentMonth = day.position == DayPosition.MonthDate

                    Day(
                        day,
                        isSelected = selectedDay == day,
                        isFromCurrentMonth = isFromCurrentMonth
                    ) { clicked ->
                        selectedDay = if (selectedDay == clicked) {
                            null
                        } else {
                            day
                        }
                    }
                },
                monthHeader = {
                    MonthHeader(daysOfWeek = daysOfWeek)
                },
            )
        }
        Text(
            "Rincian",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(bottom = 64.dp)) {
            item {
                GroupedCashFlowCard(
                    entities = cashOutEntities,
                    isCashIn = false,
                )
            }
            item {
                GroupedCashFlowCard(
                    entities = cashInEntities,
                    isCashIn = true,
                )
            }
        }
    }
}

val cashOutEntities = listOf(
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = false,
        amount = 13000L,
        created = "30-10-2024"
    ),
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = false,
        amount = 13000L,
        created = "30-10-2024"
    ),
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = false,
        amount = 13000L,
        created = "30-10-2024"
    ),
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = false,
        amount = 13000L,
        created = "30-10-2024"
    ),
)

val cashInEntities = listOf(
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = true,
        amount = 13000L,
        created = "30-10-2024"
    ),
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = true,
        amount = 13000L,
        created = "30-10-2024"
    ),
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = true,
        amount = 13000L,
        created = "30-10-2024"
    ),
    CashFlow(
        id = "asdads",
        text = "Beli telur 1kg",
        isCashIn = true,
        amount = 13000L,
        created = "30-10-2024"
    ),
)

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color(0xFF29515B),
                fontSize = 12.sp,
                text = dayOfWeek.toString().advancedTitleCase().substring(0..2),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isFromCurrentMonth: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .testTag("MonthDay")
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color(0xFFBFFF67) else Color.Transparent)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = true,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (isFromCurrentMonth) Color.Black else Color.Gray,
            fontSize = 14.sp,
        )
    }
}

@Preview
@Composable
private fun Example1Preview() {
    CalendarContent()
}