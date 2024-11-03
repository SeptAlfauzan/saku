package org.kudos.saku.app.presentation.views.stats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.koalaplot.core.pie.CircularLabelPositionProvider
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.pie.PieLabelPlacement
import io.github.koalaplot.core.pie.StraightLineConnector
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

class StatsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        StatsView(navigateBack = {
            navigator.pop()
        })
    }
}

val fibonacci = listOf(0f, 1f, 1f, 2f, 3f, 5f, 8f, 13f, 21f, 34f, 55f, 89f, 144f)
val padding = 16.dp

data class ChartItem<T>(val data: T, val isShowed: Boolean, val chartColor: Color)

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun StatsView(
    navigateBack: () -> Unit, modifier: Modifier = Modifier
) {
    var selectedIndex: Int? by remember { mutableStateOf(null) }
    val data = fibonacci.mapIndexed { i, it ->
        ChartItem(
            data = it, isShowed = true, chartColor = materialColors[i]
        )
    }
    var dataState: List<ChartItem<Float>> by remember { mutableStateOf(data) }
    val activeData = dataState.filter { it.isShowed }
    val activeDataValue = dataState.filter { it.isShowed }.map { it.data }

    fun updateDataVisibility(index: Int, isShowed: Boolean) {
        selectedIndex = null
        dataState =
            dataState.mapIndexed { i, chartItem -> if (i == index) chartItem.copy(isShowed = isShowed) else chartItem }
    }
    Scaffold(topBar = { TopBar(navigateBack) }) {
        Column(Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Card(
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(width = 0.4.dp, color = Color(0xFFA6A6A6)),
                modifier = modifier.fillMaxWidth().padding(vertical = 24.dp)
            ) {
                PieChart(
                    labelConnector = { StraightLineConnector(connectorColor = Color(0xFFA6A6A6)) },
                    labelPositionProvider = CircularLabelPositionProvider(
                        labelSpacing = 1f,
                        labelPlacement = PieLabelPlacement.InternalOrExternal(),
                    ),
                    values = activeDataValue,
                    slice = { i: Int ->
                        DefaultSlice(
                            hoverExpandFactor = 1.05f,
                            hoverElement = { Text(activeDataValue[i].toString()) },
                            antiAlias = true,
                            color = activeData.getOrNull(i)?.chartColor ?: Color.LightGray,
                            gap = 0f,
                            onClick = {
                                (if (selectedIndex == i) null else i).also { selectedIndex = it }
                            },
                            clickable = true
                        )
                    },
                    forceCenteredPie = true,
                    holeSize = 0.8f,
                    label = { i ->
                        AnimatedVisibility(selectedIndex == i) {
                            Text((activeData.getOrNull(i)?.data ?: "").toString())
                        }
                    },
                )
            }

            LazyColumn(
                modifier = Modifier.padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(dataState,
                    key = { index: Int, _: ChartItem<Float> -> index }) { index, it ->
                    StatItem(text = it.data.toString(),
                        isShowedInChart = it.isShowed,
                        onChecked = { newValue ->
                            updateDataVisibility(index = index, newValue)
                        })
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    navigateBack: () -> Unit,
) {
    TopAppBar(elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background,
        title = {},
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack, contentDescription = "arrow nav back"
                )
            }
        })
}

@Composable
private fun StatItem(
    text: String,
    onChecked: (Boolean) -> Unit,
    isShowedInChart: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 0.4.dp, color = Color(0xFFA6A6A6)),
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
            Text(
                "Item",
                style = TextStyle(Color(0xFFA6A6A6)),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Show in chart", style = TextStyle(Color(0xFFA6A6A6)))
                    Switch(
                        checked = isShowedInChart,
                        onCheckedChange = onChecked,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFFBFFF67)
                        )
                    )
                }
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
