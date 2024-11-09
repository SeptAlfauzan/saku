package org.kudos.saku.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
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
    private val _groupedSelectedCashFlowEntities: MutableStateFlow<UIState<Pair<List<CashFlow>, List<CashFlow>>>> =
        MutableStateFlow(UIState.Loading)
    val groupedSelectedCashFlowEntities: StateFlow<UIState<Pair<List<CashFlow>, List<CashFlow>>>> =
        _groupedSelectedCashFlowEntities

    private val _dashboardCashFlowReport: MutableStateFlow<UIState<Pair<Long, Long>>> =
        MutableStateFlow(UIState.Loading)
    val dashboardCashFlowReport: StateFlow<UIState<Pair<Long, Long>>> = _dashboardCashFlowReport

    private val _cashInFlowEntities: MutableStateFlow<UIState<List<CashFlow>>> =
        MutableStateFlow(UIState.Loading)
    val cashInFlowEntities: StateFlow<UIState<List<CashFlow>>> = _cashInFlowEntities
    private val _cashOutFlowEntities: MutableStateFlow<UIState<List<CashFlow>>> =
        MutableStateFlow(UIState.Loading)
    val cashOutFlowEntities: StateFlow<UIState<List<CashFlow>>> = _cashOutFlowEntities


    fun getCashFlowEntities() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cashFlowEntities.value = UIState.Loading
                cashFlowRepository.getCashFlow().catch {
                    _cashFlowEntities.value = UIState.Error(it.message ?: "Error")
                }.collect {
                    Napier.d { "data: $it" }
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

    fun getCashFlowEntitiesByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cashFlowEntities.value = UIState.Loading
                cashFlowRepository.getByDate(date).catch {
                    _cashFlowEntities.value = UIState.Error(it.message ?: "Error")
                }.collect {
                    Napier.d { "data: $it" }
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

    fun getCashInFlowEntitiesByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cashInFlowEntities.value = UIState.Loading
                cashFlowRepository.getCashInByDate(date).catch {
                    _cashInFlowEntities.value = UIState.Error(it.message ?: "Error")
                }.collect {
                    Napier.d { "data: $it" }
                    _cashInFlowEntities.value = UIState.Success(it.map { cashFlowEntity ->
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
                _cashInFlowEntities.value = UIState.Error(e.message ?: "Error")
            }
        }
    }

    fun getCashOutFlowEntitiesByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cashOutFlowEntities.value = UIState.Loading
                cashFlowRepository.getCashOutByDate(date).catch {
                    _cashOutFlowEntities.value = UIState.Error(it.message ?: "Error")
                }.collect {
                    Napier.d { "data: $it" }
                    _cashOutFlowEntities.value = UIState.Success(it.map { cashFlowEntity ->
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
                _cashOutFlowEntities.value = UIState.Error(e.message ?: "Error")
            }
        }
    }



    fun getGroupedCashFlowEntitiesByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _groupedSelectedCashFlowEntities.value = UIState.Loading
                cashFlowRepository.getGroupedCashInCashOutByDate(date).catch {
                    _groupedSelectedCashFlowEntities.value = UIState.Error(it.message ?: "Error")
                }.collect {
                    val cashInEntities = it.second.map { cashFlowEntity ->
                        CashFlow(
                            id = cashFlowEntity.id,
                            text = cashFlowEntity.text,
                            amount = cashFlowEntity.amount,
                            isCashIn = cashFlowEntity.isCashIn,
                            created = cashFlowEntity.created
                        )
                    }
                    val cashOutEntities = it.first.map { cashFlowEntity ->
                        CashFlow(
                            id = cashFlowEntity.id,
                            text = cashFlowEntity.text,
                            amount = cashFlowEntity.amount,
                            isCashIn = cashFlowEntity.isCashIn,
                            created = cashFlowEntity.created
                        )
                    }
                    _groupedSelectedCashFlowEntities.value =
                        UIState.Success(Pair(cashOutEntities, cashInEntities))
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
                withContext(Dispatchers.Main) {
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

    fun deleteCashFlow(item: CashFlow, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isSavingCashFlowEntity.value = true
                val entity = CashFlowEntity(
                    id = item.id,
                    text = item.text,
                    amount = item.amount,
                    created = item.created,
                    isCashIn = item.isCashIn
                )
                cashFlowRepository.deleteCashFlow(entity)
                withContext(Dispatchers.Main) {
                    onSuccess()
                    Napier.i("viewmodel $item success")
                }
            } catch (e: Exception) {
                Napier.e { "Error $e" }
                withContext(Dispatchers.Main) {
                    onFail(e.message ?: "Error")
                }
            } finally {
                _isSavingCashFlowEntity.value = false
            }
        }
    }


    fun getReportTodayAndMonthlyCashFlow(date: String) {
        val month = date.slice(5..6)
        val year = date.slice(0..3).toInt()

        Napier.d { "month: $month" }

        viewModelScope.launch(Dispatchers.IO) {
            _dashboardCashFlowReport.value = UIState.Loading

            val currentDateData = cashFlowRepository.getCurrentDateDifference(date)
            val currentMonthData = cashFlowRepository.getCurrentMonthDifference(month, year)
            currentDateData.combine(currentMonthData, transform = { dateData, monthData ->
                Pair(
                    dateData,
                    monthData
                )
            }).catch { err ->
                _dashboardCashFlowReport.value = UIState.Error(err.message ?: "Error, try again")
            }.collect { data ->
                _dashboardCashFlowReport.value = UIState.Success(data)
            }
        }
    }

    fun getMonthlyCashFlowReport() {
        viewModelScope.launch(Dispatchers.IO) {
            cashFlowRepository.getMonthlyCashFlowReport().catch {
                Napier.e { "Error: $it" }
            }.collect {
                Napier.d { "difference $it" }
            }
        }
    }
}