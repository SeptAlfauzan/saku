package org.kudos.saku.app.statistic.presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.kudos.saku.app.presentation.widgets.common.ButtonType
import org.kudos.saku.app.presentation.widgets.common.PillButton

@Composable
fun StatsHeader(
    currentDateString: String,
    triggerBottomSheet: () -> Unit,
    filterIsExpense: Boolean,
    setFilterIsExpense: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Your expenses")
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = triggerBottomSheet,
                elevation = ButtonDefaults.elevation(0.dp),
                border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(currentDateString)
                    Icon(Icons.Default.DateRange, contentDescription = null)
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PillButton(
                "Expense",
                onClick = { setFilterIsExpense(true) },
                type = if (filterIsExpense) ButtonType.DEFAULT else ButtonType.OUTLINED
            )
            PillButton(
                "Income",
                onClick = { setFilterIsExpense(false) },
                type = if (!filterIsExpense) ButtonType.DEFAULT else ButtonType.OUTLINED
            )
        }
    }
}