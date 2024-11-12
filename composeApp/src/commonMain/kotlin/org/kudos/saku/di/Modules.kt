package org.kudos.saku.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.kudos.saku.app.data.repositories.CashFlowRepositoryImpl
import org.kudos.saku.app.data.repositories.SettingRepositoryImpl
import org.kudos.saku.app.domain.repositories.CashFlowRepository
import org.kudos.saku.app.domain.repositories.SettingRepository
import org.kudos.saku.app.presentation.viewmodels.CashFlowViewModel
import org.kudos.saku.app.setting.presentation.SettingViewModel

expect val platformModule: Module
val sharedModule = module {
    single<SettingRepository> {
        SettingRepositoryImpl(get())
    }
    single<CashFlowRepository> {
        CashFlowRepositoryImpl(get())
    }
    viewModel {
        SettingViewModel(
            get()
        )
    }
    viewModel {
        CashFlowViewModel(get())
    }
}