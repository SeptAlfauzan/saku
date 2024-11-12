package org.kudos.saku.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao
import org.kudos.saku.shared.DataStorePlatform
import org.kudos.saku.shared.DatabaseClient

actual val platformModule: Module = module {
    single<CashFlowDao> { DatabaseClient().getDatabaseInstance().cashFlowDao() }
    single<DataStorePlatform> { DataStorePlatform() }
}