package com.example.finalprogect_shift.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprogect_shift.domain.usecases.auth.RegistrationUseCase
import com.example.finalprogect_shift.presentation.BaseStates
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.HttpException

class SignUpViewModel(
    private val registrationUseCase: RegistrationUseCase,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableLiveData<BaseStates<ResponseBody>> = MutableLiveData()
    val state: LiveData<BaseStates<ResponseBody>> = _state

    private val ioScope = CoroutineScope(ioDispatcher)

    fun signUpBtnOnClick(name: String, password: String) {
        if (name.isEmpty() || password.isEmpty()) {
            _state.value = BaseStates.EmptyField
        } else {
            _state.value = BaseStates.FilledField
            viewModelScope.launch {
                checkRegistration(name, password)
            }
        }
    }

    private suspend fun checkRegistration(name: String, password: String) {
        try {
            _state.value = BaseStates.Loading
            _state.value = withContext(ioDispatcher){
                async {
                    return@async BaseStates.SuccessResponseBody(registrationUseCase(name, password))
                }.await()
            }
        } catch (e: Throwable) {
            when (e) {
                is HttpException -> _state.value =
                    BaseStates.Error(false, e.code(), e.response()?.errorBody())
                else -> _state.value = BaseStates.Error(true, null, null)
            }
        }
    }

}