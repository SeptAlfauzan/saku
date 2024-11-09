package org.kudos.saku.app.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity
import org.kudos.saku.app.domain.entities.MonthlyCashFlow
import org.kudos.saku.app.domain.repositories.CashFlowRepository

class CashFlowRepositoryImpl (override val cashFlowDao: CashFlowDao) :
    CashFlowRepository {
    override suspend fun getCashFlow(): Flow<List<CashFlowEntity>> = cashFlowDao.getAllAsFlow()
    override suspend fun getCashInByDate(date: String): Flow<List<CashFlowEntity>> = cashFlowDao.getCashInByDate(date)
    override suspend fun getCashOutByDate(date: String): Flow<List<CashFlowEntity>> = cashFlowDao.getCashOutByDate(date)

    override suspend fun deleteCashFlow(item: CashFlowEntity) {
        cashFlowDao.delete(item)
    }

    override suspend fun insertCashFlow(item: CashFlowEntity) {
        cashFlowDao.insert(item)
    }

    override suspend fun getByDate(date: String): Flow<List<CashFlowEntity>> =
        cashFlowDao.getByDateAsFlow(date)

    override suspend fun getGroupedCashInCashOutByDate(date: String): Flow<Pair<List<CashFlowEntity>, List<CashFlowEntity>>> {
        val entities = cashFlowDao.getByDateAsFlow(date).firstOrNull()
        val cashOutEntities = entities?.filter { !it.isCashIn } ?: listOf()
        val cashInEntities = entities?.filter { it.isCashIn } ?: listOf()
        return flowOf(Pair(cashOutEntities, cashInEntities))
    }
    override suspend fun getCurrentDateDifference(date: String): Flow<Long> = cashFlowDao.getSelectedDateCashInCashOutDifference(date)
    override suspend fun getCurrentMonthDifference(month: String, year: Int): Flow<Long> = cashFlowDao.getSelectedMonthCashInCashOutDifference(month, year)
    override suspend fun getMonthlyCashFlowReport(): Flow<List<MonthlyCashFlow>> = cashFlowDao.getMonthlyCashInCashOutDifference()


}