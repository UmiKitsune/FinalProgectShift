package com.example.finalprogect_shift.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprogect_shift.domain.models.LoanConditions
import com.example.finalprogect_shift.domain.models.ShowLoansList
import com.example.finalprogect_shift.domain.usecases.loan.GetAllLoansUseCase
import com.example.finalprogect_shift.domain.usecases.loan.GetLoanConditionUseCase
import com.example.finalprogect_shift.presentation.BaseStates
import kotlinx.coroutines.*
import retrofit2.HttpException

class MainViewModel(
    private val getLoanConditionUseCase: GetLoanConditionUseCase,
    private val getAllLoansUseCase: GetAllLoansUseCase,
    ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _loanCondition: MutableLiveData<BaseStates<LoanConditions>> = MutableLiveData()
    val loanCondition: LiveData<BaseStates<LoanConditions>> = _loanCondition

    private val _loansListData: MutableLiveData<BaseStates<ShowLoansList>> = MutableLiveData()
    val loansListData: LiveData<BaseStates<ShowLoansList>> = _loansListData

    private val ioScope = CoroutineScope(ioDispatcher)

    fun onGetLoanBtnClick(token: String) {
        _loanCondition.value = BaseStates.Loading

        viewModelScope.launch {
            try {
                _loanCondition.value = ioScope.async{
                    return@async BaseStates.SuccessResponseBody(getLoanConditionUseCase(token))
                }.await()
            } catch (e: Throwable) {
                when (e) {
                    is HttpException -> _loanCondition.value =
                        BaseStates.Error(false, e.code(), e.response()?.errorBody())
                    else -> {
                        _loanCondition.value = BaseStates.Error(true, null, null)
                    }
                }
            }
        }
    }

    fun onShowAllLoanBtnClick(token: String) {
        _loansListData.value = BaseStates.Loading

        viewModelScope.launch {
            try {
                _loansListData.value = ioScope.async{
                    return@async BaseStates.SuccessResponseBody(getAllLoansUseCase(token))
                }.await()
            } catch (e: Throwable) {
                when (e) {
                    is HttpException -> _loansListData.value =
                        BaseStates.Error(false, e.code(), e.response()?.errorBody())
                    else -> {
                        _loansListData.value = BaseStates.Error(true, null, null)
                    }
                }
            }
        }
    }

}