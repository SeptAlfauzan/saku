package org.kudos.saku.app.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity
import org.kudos.saku.app.domain.entities.MonthlyCashFlow

interface CashFlowRepository {
    val cashFlowDao: CashFlowDao
    suspend fun getCashFlow(): Flow<List<CashFlowEntity>>
    suspend fun deleteCashFlow(item: CashFlowEntity)
    suspend fun insertCashFlow(item: CashFlowEntity)
    suspend fun getByDate(date:String): Flow<List<CashFlowEntity>>
    suspend fun getGroupedCashInCashOutByDate(date: String): Flow<Pair<List<CashFlowEntity>, List<CashFlowEntity>>>
    suspend fun getCurrentDateDifference(date: String): Flow<Long>
    suspend fun getCurrentMonthDifference(month: String, year: Int): Flow<Long>
    suspend fun getMonthlyCashFlowReport() : Flow<List<MonthlyCashFlow>>
    suspend fun getCashInByDate(date:String): Flow<List<CashFlowEntity>>
    suspend fun getCashOutByDate(date:String): Flow<List<CashFlowEntity>>
}