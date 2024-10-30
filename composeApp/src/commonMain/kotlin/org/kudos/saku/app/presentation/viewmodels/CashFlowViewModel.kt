package org.kudos.saku.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.domain.repositories.CashFlowRepository
import org.kudos.saku.utils.UIState

class CashFlowViewModel(private val cashFlowRepository: CashFlowRepository) : ViewModel() {
    private val _cashFlowEntities: MutableStateFlow<UIState<List<CashFlow>>> =
        MutableStateFlow(UIState.Loading)
    val cashFlowEntities: StateFlow<UIState<List<CashFlow>>> = _cashFlowEntities
    private val _isSavingCashFlowEntity: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSavingCashFlowEntity: StateFlow<Boolean> = _isSavingCashFlowEntity

    fun getCashFlowEntities() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cashFlowEntities.value = UIState.Loading
                cashFlowRepository.getCashFlow().catch {
                    _cashFlowEntities.value = UIState.Error(it.message ?: "Error")
                }.collect {
                    _cashFlowEntities.value = UIState.Success(it.map { cashFlowEntity ->
                        CashFlow(
                            id = cashFlowEntity.id,
                            text = cashFlowEntity.text,
                            amount = cashFlowEntity.amount,
                            isCashIn = cashFlowEntity.isCashIn,
                            created = cashFlowEntity.created
                        )
                    })
                }
            } catch (e: Exception) {
                _cashFlowEntities.value = UIState.Error(e.message ?: "Error")
            }
        }
    }

    fun insertCashFlow(item: CashFlow, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Napier.i("viewmodel $item")
                _isSavingCashFlowEntity.value = true
                val entity = CashFlowEntity(
                    id = item.id,
                    text = item.text,
                    amount = item.amount,
                    created = item.created,
                    isCashIn = item.isCashIn
                )
                cashFlowRepository.insertCashFlow(entity)
                withContext(Dispatchers.Main){
                    onSuccess()
                    Napier.i("viewmodel $item success")
                }
            } catch (e: Exception) {
                // TODO: add error handler
            } finally {
                _isSavingCashFlowEntity.value = false
            }
        }
    }
}