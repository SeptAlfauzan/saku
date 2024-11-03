package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.utils.toIdr

@Composable
fun GroupedCashFlowCard(
    entities: List<CashFlow>,
    isCashIn: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 0.4.dp, color = Color(0xFFA6A6A6))
    ) {
        Column(modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Text(
                if (isCashIn) "Revenue" else "Spending",
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                style = TextStyle(Color(0xFFB9B8B8))
            )
            if (entities.isEmpty()) {
                Text(
                    "No data for this date",
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                )
            } else {
                entities.map {
                    ItemCashFlow(it)
                }
            }
        }
    }
}


@Composable
private fun ItemCashFlow(entity: CashFlow, modifier: Modifier = Modifier) {
    val borderColor = if (entity.isCashIn) Color(0xFFBFFF67) else Color(0xFFFF4D4D)
    Row(modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp).drawBehind {
        val strokeWidth = 4 * density
        val y = size.height

        drawLine(
            borderColor, Offset(0f, 0f), Offset(0f, y), strokeWidth
        )
    }, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(entity.text, modifier = Modifier.padding(start = 12.dp))
        Text(entity.amount.toIdr(), style = TextStyle())
    }
}