package org.kudos.saku.app.presentation.views.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.now
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.presentation.widgets.common.AddCashFlowRecordBottomSheet
import org.kudos.saku.app.presentation.widgets.common.ButtonType
import org.kudos.saku.app.presentation.widgets.common.PillButton
import org.kudos.saku.app.presentation.widgets.home.HomeFloatingActionButton
import org.kudos.saku.app.presentation.widgets.home.HomeTopBar
import org.kudos.saku.utils.UIState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    onSwipeDeleteEntity: (CashFlow) -> Unit,
    loadCashFlowEntitiesByDate: (date: String) -> Unit,
    cashFlowEntitiesStateFlow: StateFlow<UIState<List<CashFlow>>>,
    loadGroupSelectedCashFlowEntitiesByDate: (date: String) -> Unit,
    cashFlowGroupSelectedEntitiesStateFlow: StateFlow<UIState<Pair<List<CashFlow>, List<CashFlow>>>>,
    insertCashFlowToDB: (CashFlow, () -> Unit) -> Unit,
    isSavingCashFlowSateFlow: StateFlow<Boolean>,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 10 })
    val coroutineScope = rememberCoroutineScope()
    val menus = listOf(
        Menu(text = "Today", onClick = {
            coroutineScope.launch { pagerState.animateScrollToPage(0) }
        }),
        Menu(text = "Calendar", onClick = {
            coroutineScope.launch { pagerState.animateScrollToPage(1) }
        })
    )

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val today = CalendarDay(
        date = LocalDate.now(),
        position = DayPosition.MonthDate
    )
    LaunchedEffect(Unit) {
        loadCashFlowEntitiesByDate(today.date.toString())
    }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                HomeTopBar(onClick = {})
            },
            floatingActionButton = {
                HomeFloatingActionButton(onClick = {
                    showBottomSheet = true
                })
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) {
            Column(
                Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    itemsIndexed(menus) { index, it ->
                        PillButton(
                            it.text,
                            onClick = it.onClick,
                            type = if (index == pagerState.currentPage) ButtonType.DEFAULT else ButtonType.OUTLINED
                        )
                    }
                }
                Box(Modifier.weight(1f)) {
                    HorizontalPager(state = pagerState) { page ->
                        when (page) {
                            0 -> HomeContent(
                                todayCashFlowEntitiesStateFlow =
                                cashFlowEntitiesStateFlow,
                                onSwipeDelete = onSwipeDeleteEntity
                            )

                            1 -> CalendarContent(
                                loadCashFlowFromDate =
                                loadGroupSelectedCashFlowEntitiesByDate,
                                cashFlowGroupSelectedEntitiesStateFlow = cashFlowGroupSelectedEntitiesStateFlow
                            )
                        }
                    }
                }
            }
        }
        AddCashFlowRecordBottomSheet(
            showBottomSheet = showBottomSheet,
            insertCashFlowToDB = insertCashFlowToDB,
            isSavingCashFlow = isSavingCashFlowSateFlow,
            onSuccess = {
                scope.launch {
                    snackbarHostState.showSnackbar("Successfully add new cash flow item 🎉")
                }
            },
            onDismiss = { showBottomSheet = false })
    }
}

data class Menu(
    val text: String,
    val onClick: () -> Unit,
)