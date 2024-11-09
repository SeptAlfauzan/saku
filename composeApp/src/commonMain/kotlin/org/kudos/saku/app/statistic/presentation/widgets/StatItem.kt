package org.kudos.saku.app.statistic.presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.kudos.saku.utils.toIdr


@Composable
fun StatItem(
    text: String,
    money: Long,
    onChecked: (Boolean) -> Unit,
    isShowedInChart: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 0.4.dp, color = Color(0xFFA6A6A6)),
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {

            Text(
                "Item",
                style = TextStyle(Color(0xFFA6A6A6)),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text)
                    Text(money.toIdr())
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Show in chart", style = TextStyle(Color(0xFFA6A6A6)))
                    Switch(
                        checked = isShowedInChart,
                        onCheckedChange = onChecked,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF121212)
                        )
                    )
                }
            }
        }
    }
}