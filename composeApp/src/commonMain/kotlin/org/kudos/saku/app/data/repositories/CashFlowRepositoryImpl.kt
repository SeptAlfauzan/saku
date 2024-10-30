package org.kudos.saku.app.data.repositories

import kotlinx.coroutines.flow.Flow
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity
import org.kudos.saku.app.domain.repositories.CashFlowRepository

class CashFlowRepositoryImpl constructor(override val cashFlowDao: CashFlowDao): CashFlowRepository {
    override suspend fun getCashFlow(): Flow<List<CashFlowEntity>> = cashFlowDao.getAllAsFlow()

    override suspend fun deleteCashFlow(item: CashFlowEntity) {
        cashFlowDao.insert(item)
    }

    override suspend fun insertCashFlow(item: CashFlowEntity) {
        cashFlowDao.delete(item)
    }
}