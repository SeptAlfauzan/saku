package org.kudos.saku.app.presentation.views.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.presentation.widgets.ButtonType
import org.kudos.saku.app.presentation.widgets.PillButton
import org.kudos.saku.utils.NumberFormatter

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
        TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp,
            actions = {
                IconButton(
                    onClick = {},
                ) {
                    Icon(Icons.Default.MoreVert, "more")
                }
            }, title = {})
    }, floatingActionButton = {
        FloatingActionButton(
            shape = RoundedCornerShape(12.dp),
            onClick = {},
            backgroundColor = Color.Black,
            contentColor = Color.White,
        ) {
            Icon(Icons.Default.Add, "add icon")
        }
    }) {
        Column(Modifier.padding(horizontal = 24.dp)) {
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
                // Our page content
                when (page) {
                    0 -> HomeContent()
                    1 -> Column(Modifier.fillMaxSize()) {
                        Text("Calendar View")
                    }
                }
            }
        }
    }
}

@Composable
fun CashflowCard(
    isCashIn: Boolean,
    money: Long,
    modifier: Modifier = Modifier
) {
    val textStyle = TextStyle(if (isCashIn) Color(0xFF419F41) else Color(0xFF9F4141))
    Card(
        modifier.fillMaxWidth().padding(bottom = 12.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFFB9B8B8)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("Total pemasukan", style = textStyle)
            Row {
                Icon(
                    if (isCashIn) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color(if (isCashIn) 0xFFBFFF67 else 0xFFFF4D4D),
                    modifier = Modifier.size(34.dp),
                )
                Text(
                    money.toIdr(),
                    style = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold)
                )
            }
            Box(
                modifier = Modifier.align(Alignment.End)
                    .clip(shape = RoundedCornerShape(32.dp))
                    .background(Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("dibuat 12/12/2024", style = TextStyle(fontSize = 8.sp, color = Color.White))
            }
        }
    }
}

fun Long.toIdr(): String {
    val formatter = NumberFormatter()
    return formatter.formatIDR(this)
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
            CashflowCard(isCashIn = true, money = 25000)
        }
    }
}

@Composable
fun SeeChartCard(modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    Card(backgroundColor = Color(0xFFFFCC67)) {
        Column(modifier.padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text("Tap button bellow to see your financial flow report", style = textStyle)
            PillButton("see report", onClick = {}, radiusCorner = 32.dp)
        }
    }
}

@Composable
fun TodayCard(modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    Card(backgroundColor = Color(0xFFBFFF67)) {

        Column(modifier.padding(12.dp)) {
            Text(
                "Today", style = textStyle
            )
            Text(
                "19/10", style = textStyle.copy(
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                "2024", style = textStyle.copy(
                    fontSize = 64.sp,
                )
            )
        }
    }
}

data class Menu(
    val text: String,
    val onClick: () -> Unit,
)