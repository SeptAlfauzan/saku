package org.kudos.saku.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.kudos.saku.app.domain.repositories.CashFlowRepository
import org.kudos.saku.app.presentation.viewmodels.CashFlowViewModel
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class ViewModelFactory<R>(
    private val repository: R,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return when{
            modelClass.simpleName == CashFlowViewModel::class.simpleName -> {
                @Suppress("UNCHECKED_CAST")
                CashFlowViewModel(repository as CashFlowRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
