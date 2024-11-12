package org.kudos.saku.app.statistic.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.now
import dev.darkokoa.datetimewheelpicker.WheelDatePicker
import io.github.aakira.napier.Napier
import kotlinx.datetime.LocalDate
import org.koin.compose.koinInject
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.presentation.viewmodels.CashFlowViewModel
import org.kudos.saku.app.presentation.widgets.common.BottomSheet
import org.kudos.saku.app.statistic.domain.entities.ChartItem
import org.kudos.saku.app.statistic.presentation.widgets.RenderPieChart
import org.kudos.saku.app.statistic.presentation.widgets.StatItem
import org.kudos.saku.app.statistic.presentation.widgets.StatsHeader
import org.kudos.saku.app.presentation.widgets.common.TopBarWithBackButton
import org.kudos.saku.utils.UIState

class StatisticScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val cashFlowViewModel = koinInject<CashFlowViewModel>()
        val expensesEntities = cashFlowViewModel.cashOutFlowEntities.collectAsState().value
        val incomeEntities = cashFlowViewModel.cashInFlowEntities.collectAsState().value
        fun loadExpenses(date: String) = cashFlowViewModel.getCashOutFlowEntitiesByDate(date)
        fun loadIncome(date: String) = cashFlowViewModel.getCashInFlowEntitiesByDate(date)

        StatsView(
            expensesEntities,
            incomeEntities,
            loadExpenses = { loadExpenses(it) },
            loadIncome = { loadIncome(it) },
            navigateBack = {
                navigator.pop()
            })
    }
}

@Composable
fun StatsView(
    expensesEntities: UIState<List<CashFlow>>,
    incomeEntities: UIState<List<CashFlow>>,
    loadExpenses: (date: String) -> Unit,
    loadIncome: (date: String) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val today = CalendarDay(
        date = LocalDate.now(),
        position = DayPosition.MonthDate
    )
    var date by remember { mutableStateOf(today.date.toString()) }
    var filterIsExpense by remember { mutableStateOf(true) }
    var selectedIndex: Int? by remember { mutableStateOf(null) }
    var dataState: List<ChartItem<CashFlow>> by remember { mutableStateOf(listOf()) }
    var showBottomSheet by remember { mutableStateOf(false) }

    fun updateDataVisibility(index: Int, isShowed: Boolean) {
        selectedIndex = null
        dataState =
            dataState.mapIndexed { i, chartItem -> if (i == index) chartItem.copy(isShowed = isShowed) else chartItem }
        Napier.d { "trigger switch button $dataState" }
    }

    fun List<CashFlow>.mapToChartItemMutableList(): List<ChartItem<CashFlow>> {
        Napier.d { "triggered" }
        return this.mapIndexed { i, it ->
            ChartItem(
                data = it, isShowed = true, chartColor = materialColors[i]
            )
        }.toMutableList()
    }

    LaunchedEffect(filterIsExpense, date) {
        Napier.d { "triggered launched effect" }
        when (filterIsExpense) {
            true -> loadExpenses(date)
            false -> loadIncome(date)
        }
    }

    LaunchedEffect(expensesEntities, incomeEntities, filterIsExpense) {
        val currentState = if (filterIsExpense) expensesEntities else incomeEntities
        if (currentState is UIState.Success) {
            if (dataState.isEmpty() || dataState.map { it.data } != currentState.data
            ) {
                dataState = currentState.data.mapToChartItemMutableList()
            }
        }
    }

    val cashFlow = if (filterIsExpense) expensesEntities else incomeEntities

    Box {
        Scaffold(topBar = { TopBarWithBackButton(navigateBack) }) {
            Column(
                Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )
            ) {
                StatsHeader(
                    currentDateString = date,
                    triggerBottomSheet = { showBottomSheet = !showBottomSheet },
                    filterIsExpense = filterIsExpense,
                    setFilterIsExpense = { filterIsExpense = it },
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                cashFlow.let {
                    when (it) {
                        is UIState.Error -> Text(it.error)
                        UIState.Loading -> Box(Modifier.fillMaxSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                        is UIState.Success -> Content(
                            dataState = dataState,
                            selectedIndex = selectedIndex,
                            setSelectedIndex = { selectedIndex = it },
                            onChecked = { newValue, index ->
                                updateDataVisibility(
                                    index = index,
                                    newValue
                                )
                            },
                            modifier = modifier
                        )
                    }
                }
            }
        }
        BottomSheet(
            showBottomSheet = showBottomSheet,
            onDismiss = { showBottomSheet = false },
        ) {
            DatePicker(
                onCancel = { showBottomSheet = false },
                onOk = { selectedDate ->
                    date = selectedDate
                    showBottomSheet = false
                },
            )
        }
    }
}

@Composable
fun Content(
    dataState: List<ChartItem<CashFlow>>,
    selectedIndex: Int?,
    setSelectedIndex: (Int) -> Unit,
    onChecked: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Napier.d {
        "rerender"
    }
    Column {
        RenderPieChart(
            activeData = dataState.filter { it.isShowed },
            selectedIndex = selectedIndex,
            setSelectedIndex = setSelectedIndex,
            modifier = modifier
        )
        LazyColumn(
            modifier = Modifier.padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                dataState,
                key = { index: Int, _: ChartItem<CashFlow> -> index }) { index, it ->
                StatItem(
                    text = it.data.text,
                    money = it.data.amount,
                    isShowedInChart = it.isShowed,
                    onChecked = { onChecked(it, index) },
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    onCancel: () -> Unit,
    onOk: (date: String) -> Unit,
) {
    var date: String? by remember { mutableStateOf(null) }
    Column(Modifier.padding(24.dp).fillMaxWidth()) {
        Text("Select date")
        WheelDatePicker(modifier = Modifier.fillMaxWidth()) { snappedDate ->
            date = snappedDate.toString()
        }
        Row(horizontalArrangement = Arrangement.End) {
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = onCancel,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Text("cancel")
            }
            Button(
                enabled = date != null,
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    date?.let {
                        onOk(it)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFBFFF67)
                )
            ) {
                Text("submit")
            }
        }
    }
}


val materialColors = listOf(
    Color(0xFFF44336), // Red
    Color(0xFFE91E63), // Pink
    Color(0xFF9C27B0), // Purple
    Color(0xFF673AB7), // Deep Purple
    Color(0xFF3F51B5), // Indigo
    Color(0xFF2196F3), // Blue
    Color(0xFF03A9F4), // Light Blue
    Color(0xFF00BCD4), // Cyan
    Color(0xFF009688), // Teal
    Color(0xFF4CAF50), // Green
    Color(0xFF8BC34A), // Light Green
    Color(0xFFCDDC39), // Lime
    Color(0xFFFFEB3B), // Yellow
    Color(0xFFFFC107), // Amber
    Color(0xFFFF9800), // Orange
    Color(0xFFFF5722)  // Deep Orange
)
