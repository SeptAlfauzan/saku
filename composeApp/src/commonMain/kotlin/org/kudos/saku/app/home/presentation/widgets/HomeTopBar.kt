package org.kudos.saku.app.home.presentation.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun HomeTopBar(
    onClick: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        actions = {
            IconButton(
                onClick,
            ) {
                Icon(Icons.Default.MoreVert, "more")
            }
        },
        title = {})
}