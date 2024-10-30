package org.kudos.saku.app.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity

interface CashFlowRepository {
    val cashFlowDao: CashFlowDao
    suspend fun getCashFlow(): Flow<List<CashFlowEntity>>
    suspend fun deleteCashFlow(item: CashFlowEntity)
    suspend fun insertCashFlow(item: CashFlowEntity)
}