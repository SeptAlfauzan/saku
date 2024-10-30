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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    loadCashFlowEntities: () -> Unit,
    cashFlowEntitiesStateFlow: StateFlow<UIState<List<CashFlow>>>
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

    LaunchedEffect(Unit){
        loadCashFlowEntities()
    }

    Box(Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            HomeTopBar(onClick = {})
        }, floatingActionButton = {
            HomeFloatingActionButton(onClick = {
                showBottomSheet = true
            })
        }) {

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
                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> HomeContent(cashFlowEntitiesStateFlow)
                        1 -> CalendarContent()
                    }
                }
            }
        }
        AddCashFlowRecordBottomSheet(
            showBottomSheet = showBottomSheet,
            onDismiss = { showBottomSheet = false })
    }
}

data class Menu(
    val text: String,
    val onClick: () -> Unit,
)