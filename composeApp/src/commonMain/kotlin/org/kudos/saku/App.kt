@file:OptIn(ExperimentalUuidApi::class)

package org.kudos.saku

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.presentation.views.home.Home
import kotlin.uuid.ExperimentalUuidApi

@Composable
@Preview()
fun App(cashFlowDao: CashFlowDao) {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val cashFlow by cashFlowDao.getAllAsFlow().collectAsState(initial = emptyList())
            val mappedCashFlow = cashFlow.map { CashFlow(id=it.id, text=it.text, amount = it.amount, isCashIn = it.isCashIn) }
            Home(cashFlowEntities = mappedCashFlow)
        }
    }
}
