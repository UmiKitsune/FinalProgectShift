package com.example.finalprogect_shift.presentation.allLoans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.domain.usecases.loan.GetLoanByIdUseCase
import com.example.finalprogect_shift.presentation.BaseStates
import kotlinx.coroutines.*
import retrofit2.HttpException

class AllLoansViewModel(
    private val loanByIdUseCase: GetLoanByIdUseCase,
    ioDispatcher : CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _state = MutableLiveData<BaseStates<GetLoanResponse>>()
    val state: LiveData<BaseStates<GetLoanResponse>> = _state

    private val ioScope = CoroutineScope(ioDispatcher)

    fun onItemClick(token: String, id: Int) {
        _state.value = BaseStates.Loading

        viewModelScope.launch {
            try {
                _state.value = ioScope.async {
                    return@async BaseStates.SuccessResponseBody(loanByIdUseCase(token, id))
                }.await()
            } catch (e: Throwable) {
                when (e) {
                    is HttpException -> _state.value =
                        BaseStates.Error(false, e.code(), e.response()?.errorBody())
                    else -> {
                        _state.value = BaseStates.Error(true, null, null)
                    }
                }
            }

        }
    }


}