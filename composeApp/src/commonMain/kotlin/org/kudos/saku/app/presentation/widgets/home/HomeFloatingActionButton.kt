package org.kudos.saku.app.presentation.widgets.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeFloatingActionButton(
    onClick: () -> Unit
){
    FloatingActionButton(
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        backgroundColor = Color.Black,
        contentColor = Color.White,
    ) {
        Icon(Icons.Default.Add, "add icon")
    }
}