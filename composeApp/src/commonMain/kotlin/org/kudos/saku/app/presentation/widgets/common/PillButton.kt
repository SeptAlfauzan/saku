package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PillButton(
    text: String,
    onClick: () -> Unit,
    type: ButtonType = ButtonType.DEFAULT,
    radiusCorner: Dp = 4.dp,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick, shape = RoundedCornerShape(radiusCorner), modifier = modifier, colors = if (type == ButtonType.OUTLINED) ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Black,
        ) else ButtonDefaults.buttonColors(
            backgroundColor = if (type == ButtonType.DEFAULT) Color.Black else Color.White,
        ),
        border = BorderStroke( if (type == ButtonType.DEFAULT) 1.dp else 0.dp, Color.Black),
    ) {
        Text(
            text,
            style = TextStyle(color = if (type == ButtonType.DEFAULT) Color.White else Color.Black)
        )
    }
}

enum class ButtonType {
    OUTLINED, DEFAULT
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Column {
            PillButton(text = "test", onClick = {})
        }
    }
}