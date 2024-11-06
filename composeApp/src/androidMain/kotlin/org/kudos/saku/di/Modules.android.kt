package org.kudos.saku.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.kudos.saku.shared.DatabaseClient

actual val platformModule: Module = module {
    single { DatabaseClient(context = get()) }
}