package com.example.finalprogect_shift.presentation.getloan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprogect_shift.domain.models.GetLoanRequest
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.domain.usecases.loan.GetLoanUseCase
import com.example.finalprogect_shift.presentation.BaseStates
import kotlinx.coroutines.*
import retrofit2.HttpException

class GetLoanViewModel(
    private val getLoanUseCase: GetLoanUseCase,
    ioDispatcher : CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _state: MutableLiveData<BaseStates<GetLoanResponse>> = MutableLiveData()
    val state: LiveData<BaseStates<GetLoanResponse>> = _state

    private val ioScope = CoroutineScope(ioDispatcher)

     fun onGetLoanBtnClick(token: String, loanRequest: GetLoanRequest, maxAmount: Int) {
         if (loanRequest.amount == 0
             || loanRequest.firstName.isEmpty()
             || loanRequest.lastName.isEmpty()
             || loanRequest.phoneNumber.isEmpty()
         ) {
             _state.value = BaseStates.EmptyField
         } else if (loanRequest.amount > maxAmount) {
             _state.value = BaseStates.OutOfAmountRange
         } else {
             _state.value = BaseStates.FilledField

             viewModelScope.launch {
                 doInBackground(token, loanRequest)
             }
         }
    }

    private suspend fun doInBackground(token: String, loanRequest: GetLoanRequest) {
        _state.value = BaseStates.Loading
        try {
            _state.value = ioScope.async{
                BaseStates.SuccessResponseBody(getLoanUseCase(token, loanRequest))
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