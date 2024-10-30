@file:OptIn(ExperimentalUuidApi::class)

package org.kudos.saku

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.presentation.viewmodels.CashFlowViewModel
import org.kudos.saku.app.presentation.views.home.Home
import kotlin.uuid.ExperimentalUuidApi

@Composable
@Preview()
fun App(cashFlowViewModel: CashFlowViewModel) {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Home(
                loadCashFlowEntities = { cashFlowViewModel.getCashFlowEntities() },
                cashFlowEntitiesStateFlow = cashFlowViewModel.cashFlowEntities,
            )
        }
    }
}
