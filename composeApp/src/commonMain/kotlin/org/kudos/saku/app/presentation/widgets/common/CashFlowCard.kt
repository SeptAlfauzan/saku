package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kudos.saku.utils.toIdr

@Composable
fun CashFlowCard(
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
            DateCreated(
                text = "dibuat 12/12/2024",
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun DateCreated(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(32.dp))
            .background(Color.Black)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, style = TextStyle(fontSize = 8.sp, color = Color.White))
    }
}
