package org.kudos.saku.app.presentation.views.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.presentation.widgets.common.ButtonType
import org.kudos.saku.app.presentation.widgets.common.CashFlowCard
import org.kudos.saku.app.presentation.widgets.common.PillButton
import org.kudos.saku.app.presentation.widgets.home.HomeFloatingActionButton
import org.kudos.saku.app.presentation.widgets.home.HomeTopBar
import org.kudos.saku.app.presentation.widgets.setting.SeeChartCard
import org.kudos.saku.app.presentation.widgets.setting.TodayCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home() {
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    val coroutineScope = rememberCoroutineScope()
    val menus = listOf(
        Menu(text = "Today", onClick = {
            coroutineScope.launch { pagerState.animateScrollToPage(0) }
        }),
        Menu(text = "Calendar", onClick = {
            coroutineScope.launch { pagerState.animateScrollToPage(1) }
        })
    )
    Scaffold(topBar = {
        HomeTopBar(onClick = {})
    }, floatingActionButton = {
        HomeFloatingActionButton(onClick = {})
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
                    0 -> HomeContent()
                    1 -> CalendarContent()
                }
            }
        }
    }
}


@Preview
@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    LazyColumn {
        item {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(bottom = 36.dp).height(204.dp).fillMaxWidth()
                ) {
                    TodayCard(modifier.fillMaxHeight().width(200.dp))
                    SeeChartCard(modifier.fillMaxHeight().weight(1f))
                }
                Text(
                    "Total financial flow",
                    style = textStyle,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }
        items(10) {
            CashFlowCard(isCashIn = true, money = 25000)
        }
    }
}


data class Menu(
    val text: String,
    val onClick: () -> Unit,
)