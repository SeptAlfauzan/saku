package org.kudos.saku.app.home.presentation.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.presentation.widgets.common.CashFlowCard
import org.kudos.saku.app.statistic.presentation.StatsScreen
import org.kudos.saku.utils.UIState
import org.kudos.saku.utils.WindowSize
import org.kudos.saku.utils.rememberWindowSize

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun HomeContent(
    todayAndMonthCashFlowReport: StateFlow<UIState<Pair<Long, Long>>>,
    loadTodayAndMonthCashFlowReport: (date: String) -> Unit,
    onSwipeDelete: (CashFlow) -> Unit,
    todayCashFlowEntitiesStateFlow: StateFlow<UIState<List<CashFlow>>>,
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    val density = LocalDensity.current


    BoxWithConstraints {
        val screenWidth = maxWidth
        val windowSize = rememberWindowSize(screenWidth)

        todayCashFlowEntitiesStateFlow.collectAsState().value.let { cashFlowEntitiesUiState ->
            when (windowSize) {
                WindowSize.COMPACT -> RenderCashFlowEntities(
                    cashFlowEntitiesUiState, onSwipeDelete = onSwipeDelete, modifier
                ) {
                    TodayCard(
                        todayAndMonthCashFlowReport,
                        loadTodayAndMonthCashFlowReport,
                        navigateStatsView = { navigator.push(StatsScreen()) },
                        modifier.fillMaxSize()
                    )
                }
                else -> Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    Box(Modifier.weight(1f)) {
                        TodayCard(
                            todayAndMonthCashFlowReport,
                            loadTodayAndMonthCashFlowReport,
                            navigateStatsView = { navigator.push(StatsScreen()) },
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        RenderCashFlowEntities(
                            cashFlowEntitiesUiState,
                            onSwipeDelete = onSwipeDelete,
                            modifier = modifier
                        )
                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RenderCashFlowEntities(
    cashFlowEntitiesUiState: UIState<List<CashFlow>>,
    onSwipeDelete: (CashFlow) -> Unit,
    modifier: Modifier = Modifier,
    bannerCard: @Composable () -> Unit = {},
) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    LazyColumn(verticalArrangement = Arrangement.Top) {
        item {
            bannerCard()
            Text(
                "Total financial flow",
                style = textStyle,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
        when (cashFlowEntitiesUiState) {
            is UIState.Error -> item {
                Text(cashFlowEntitiesUiState.error)
            }

            is UIState.Loading -> item {
                Box(Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is UIState.Success -> {
                if (cashFlowEntitiesUiState.data.isEmpty()) {
                    item {
                        Text("No data added today")
                    }
                } else {
                    items(cashFlowEntitiesUiState.data, key = { item -> item.id }) {
                        CashFlowCard(
                            modifier = modifier.animateItemPlacement(),
                            onSwipeDelete = { onSwipeDelete(it) },
                            isCashIn = it.isCashIn,
                            money = it.amount,
                            created = it.created
                        )
                    }
                }
            }
        }
    }
}
