package org.kudos.saku.app.presentation.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.presentation.widgets.common.CashFlowCard
import org.kudos.saku.app.presentation.widgets.setting.SeeChartCard
import org.kudos.saku.app.presentation.widgets.setting.TodayCard


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
