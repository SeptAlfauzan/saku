package org.kudos.saku.app.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity
import org.kudos.saku.app.domain.entities.MonthlyCashFlow
import org.kudos.saku.app.domain.repositories.CashFlowRepository
import org.kudos.saku.shared.DatabaseClient

class CashFlowRepositoryImpl (override val databaseClient: DatabaseClient) :
    CashFlowRepository {
    override suspend fun getCashFlow(): Flow<List<CashFlowEntity>> =  databaseClient.getDatabaseInstance().cashFlowDao().getAllAsFlow()

    override suspend fun deleteCashFlow(item: CashFlowEntity) {
        databaseClient.getDatabaseInstance().cashFlowDao().delete(item)
    }

    override suspend fun insertCashFlow(item: CashFlowEntity) {
        databaseClient.getDatabaseInstance().cashFlowDao().insert(item)
    }

    override suspend fun getByDate(date: String): Flow<List<CashFlowEntity>> =
        databaseClient.getDatabaseInstance().cashFlowDao().getByDateAsFlow(date)

    override suspend fun getGroupedCashInCashOutByDate(date: String): Flow<Pair<List<CashFlowEntity>, List<CashFlowEntity>>> {
        val entities = databaseClient.getDatabaseInstance().cashFlowDao().getByDateAsFlow(date).firstOrNull()
        val cashOutEntities = entities?.filter { !it.isCashIn } ?: listOf()
        val cashInEntities = entities?.filter { it.isCashIn } ?: listOf()
        return flowOf(Pair(cashOutEntities, cashInEntities))
    }
    override suspend fun getCurrentDateDifference(date: String): Flow<Long> = databaseClient.getDatabaseInstance().cashFlowDao().getSelectedDateCashInCashOutDifference(date)
    override suspend fun getCurrentMonthDifference(month: String, year: Int): Flow<Long> = databaseClient.getDatabaseInstance().cashFlowDao().getSelectedMonthCashInCashOutDifference(month, year)
    override suspend fun getMonthlyCashFlowReport(): Flow<List<MonthlyCashFlow>> = databaseClient.getDatabaseInstance().cashFlowDao().getMonthlyCashInCashOutDifference()
}