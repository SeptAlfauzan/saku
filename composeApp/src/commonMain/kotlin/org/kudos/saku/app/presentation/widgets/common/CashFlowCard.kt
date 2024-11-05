package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.kudos.saku.utils.toIdr
import kotlin.math.roundToInt

enum class DragValue { START, CENTER, END }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CashFlowCard(
    isCashIn: Boolean,
    money: Long,
    created: String,
    onSwipeDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val draggableState: AnchoredDraggableState<DragValue> = remember {
        AnchoredDraggableState(
            initialValue = DragValue.START,
            positionalThreshold = { distance: Float -> distance * 0.5f } as (Float) -> Float,
            velocityThreshold = { with(density) { 100.dp.toPx() } } as () -> Float,
            animationSpec = tween(durationMillis = 300),
        )
    }
    val coroutineScope = rememberCoroutineScope()
    val anchor = remember(density) {
        DraggableAnchors {
            DragValue.START at (-0).dp.value
            DragValue.CENTER at 500.dp.value
            DragValue.END at 1000.dp.value
        }
    }
    SideEffect {
        draggableState.updateAnchors(anchor)
        when (draggableState.targetValue) {
            DragValue.END -> onSwipeDelete()
            DragValue.CENTER -> coroutineScope.launch {  draggableState.animateTo(DragValue.END) }
            DragValue.START -> null
        }
    }

    val textStyle = TextStyle(if (isCashIn) Color(0xFF419F41) else Color(0xFF9F4141))
    Box(modifier) {
        Text(
            "Delete this record",
            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = Color(0xFFD9D9D9)),
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Card(
            modifier.anchoredDraggable(
                draggableState,
                Orientation.Horizontal
            )
                .offset {
                    IntOffset(
                        x = draggableState.requireOffset().roundToInt(),
                        y = 0
                    )
                }
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            border = BorderStroke(width = 1.dp, color = Color(0xFFB9B8B8)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                Text(if (isCashIn) "Cash in" else "Cash out", style = textStyle)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowForward,
                        modifier = Modifier.rotate( if (isCashIn) -45f else 45f),
                        contentDescription = null,
                        tint = Color( if (isCashIn) 0xFF29515B else 0xFFFF4D4D)
                    )
                    Column {
                        Text(
                            money.toIdr(),
                            style = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                DateCreated(
                    text = "created $created",
                    modifier = Modifier.align(Alignment.End)
                )
            }
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
