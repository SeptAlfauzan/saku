package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun AddCashFlowRecordBottomSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    animationSpec: AnimationSpec<Float> = tween(300),

    ) {
    var sheetHeight by remember { mutableStateOf(0f) }
    var dragOffset by remember { mutableStateOf(0f) }
    val animatedOffset by animateFloatAsState(
        targetValue = if (showBottomSheet) 0f else sheetHeight,
        animationSpec = animationSpec,
        label = "bottomSheet"
    )
    if (showBottomSheet) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectTapGestures {
                        onDismiss()
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            if (dragOffset > sheetHeight * 0.25f) {
                                onDismiss()
                            }
                            dragOffset = 0f
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragOffset = (dragOffset + dragAmount.y).coerceAtLeast(0f)
                        }
                    )
                }
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)

                    .offset { IntOffset(0, (animatedOffset + dragOffset).roundToInt()) }
                    .onGloballyPositioned { coordinates ->
                        sheetHeight = coordinates.size.height.toFloat()
                    }
                    .pointerInput(Unit) {
                        // Stop tap propagation to scrim for the sheet content
                        detectTapGestures { }

                    },
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = MaterialTheme.colors.surface,
                elevation = if (showBottomSheet) 8.dp else 0.dp,

                ) {
                Column(
                    Modifier.fillMaxWidth()
                        .padding(vertical = 32.dp, horizontal = 24.dp)
                ) {

                    Text("This is bottom sheet")
                }
            }
        }
    }
}