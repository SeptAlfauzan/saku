package org.kudos.saku

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.presentation.viewmodels.CashFlowViewModel
import org.kudos.saku.app.presentation.views.home.Home

@Composable
@Preview()
fun App(cashFlowViewModel: CashFlowViewModel) {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Home(
                onSwipeDeleteEntity = {
                    cashFlowViewModel.deleteCashFlow(it, onSuccess = {
                        Napier.i { "Success" }
                    }, onFail = {
                        Napier.e { it }
                    })
                },
                loadCashFlowEntitiesByDate = { cashFlowViewModel.getCashFlowEntitiesByDate(it) },
                cashFlowEntitiesStateFlow = cashFlowViewModel.cashFlowEntities,
                loadGroupSelectedCashFlowEntitiesByDate = {
                    cashFlowViewModel.getGroupedCashFlowEntitiesByDate(
                        it
                    )
                },
                cashFlowGroupSelectedEntitiesStateFlow = cashFlowViewModel.groupedSelectedCashFlowEntities,
                insertCashFlowToDB = { it, cb -> cashFlowViewModel.insertCashFlow(it, cb) },
                isSavingCashFlowSateFlow = cashFlowViewModel.isSavingCashFlowEntity,

                )
        }
    }
}
