package org.kudos.saku.app.statistic.presentation.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.koalaplot.core.pie.CircularLabelPositionProvider
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.pie.PieLabelPlacement
import io.github.koalaplot.core.pie.StraightLineConnector
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.statistic.domain.entities.ChartItem
import org.kudos.saku.utils.toIdr

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun RenderPieChart(
    activeData: List<ChartItem<CashFlow>>,
    selectedIndex: Int?,
    setSelectedIndex: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalMoney = if (activeData.isEmpty()) 0L else activeData.map { it.data.amount }
        .reduce { acc, l -> acc + l }

    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 0.4.dp, color = Color(0xFFA6A6A6)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            Box(Modifier) {
                PieChart(
                    labelConnector = { StraightLineConnector(connectorColor = Color(0xFFA6A6A6)) },
                    labelPositionProvider = CircularLabelPositionProvider(
                        labelSpacing = 1f,
                        labelPlacement = PieLabelPlacement.InternalOrExternal(),
                    ),
                    values = activeData.map { it.data.amount.toFloat() },
                    slice = { i: Int ->
                        DefaultSlice(
                            hoverExpandFactor = 1.05f,
                            hoverElement = { Text(activeData[i].data.text) },
                            antiAlias = true,
                            color = activeData.getOrNull(i)?.chartColor ?: Color.LightGray,
                            gap = 0f,
                            clickable = true,
                            onClick = {
                                (if (selectedIndex == i) null else i).also {
                                    setSelectedIndex(i)
                                }
                            },
                        )
                    },
                    forceCenteredPie = true,
                    holeSize = 0.8f,
                )
                TotalText(
                    totalMoney, Modifier.align(
                        Alignment.Center
                    )
                )
            }
            AnimatedVisibility(selectedIndex != null) {
                selectedIndex?.let { index ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            Modifier
                                .size(32.dp)
                                .clip(
                                    CircleShape
                                ).background(
                                    activeData.getOrNull(index)?.chartColor ?: Color.Transparent
                                )
                        )
                        Column {
                            Text((activeData.getOrNull(index)?.data?.text ?: ""))
                            Text((activeData.getOrNull(index)?.data?.amount?.toIdr() ?: ""))
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun TotalText(totalMoney: Long, modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Color(0xFF29515B)
    )
    Column(
        modifier = modifier
    ) {
        Text("Total")
        Text(
            totalMoney.toIdr(),
            style = textStyle.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
    }
}