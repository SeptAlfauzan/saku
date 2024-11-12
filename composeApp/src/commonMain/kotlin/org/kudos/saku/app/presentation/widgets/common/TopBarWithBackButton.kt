package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun TopBarWithBackButton(
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