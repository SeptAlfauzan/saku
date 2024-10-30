package org.kudos.saku.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.domain.repositories.CashFlowRepository
import org.kudos.saku.utils.UIState

class CashFlowViewModel(private val cashFlowRepository: CashFlowRepository) : ViewModel() {
    private val _cashFlowEntities: MutableStateFlow<UIState<List<CashFlow>>> =
        MutableStateFlow<UIState<List<CashFlow>>>(UIState.Loading)
    val cashFlowEntities: StateFlow<UIState<List<CashFlow>>> = _cashFlowEntities

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
                            isCashIn = cashFlowEntity.isCashIn
                        )
                    })
                }
            } catch (e: Exception) {
                _cashFlowEntities.value = UIState.Error(e.message ?: "Error")
            }
        }
    }

    fun insertCashFlow(item: CashFlow){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}