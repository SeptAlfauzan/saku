package org.kudos.saku.app.presentation.views.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.presentation.views.stats.StatsScreen
import org.kudos.saku.app.presentation.widgets.common.CashFlowCard
import org.kudos.saku.app.presentation.widgets.setting.StatsCard
import org.kudos.saku.app.presentation.widgets.setting.TodayCard
import org.kudos.saku.utils.UIState

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun HomeContent(
    todayAndMonthCashFlowReport: StateFlow<UIState<Pair<Long,Long>>>,
    loadTodayAndMonthCashFlowReport: (date: String) -> Unit,
    onSwipeDelete: (CashFlow) -> Unit,
    todayCashFlowEntitiesStateFlow: StateFlow<UIState<List<CashFlow>>>,
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    todayCashFlowEntitiesStateFlow.collectAsState().value.let { cashFlowEntitiesUiState ->
        LazyColumn(verticalArrangement = Arrangement.Top) {
            item {
                Column(Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 36.dp).height(204.dp).fillMaxWidth()
                    ) {
                        TodayCard(
                            modifier.fillMaxHeight().width(200.dp))
                        StatsCard(
                            todayAndMonthCashFlowReport,
                            loadTodayAndMonthCashFlowReport,
                            navigateStatsView = { navigator.push(StatsScreen()) },
                            modifier.fillMaxHeight().weight(1f)
                        )
                    }
                    Text(
                        "Total financial flow",
                        style = textStyle,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
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
}
