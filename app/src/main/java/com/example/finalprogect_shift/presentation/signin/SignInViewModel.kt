package com.example.finalprogect_shift.presentation.signin

import androidx.lifecycle.*
import com.example.finalprogect_shift.data.storage.UserPreferencesStorage
import com.example.finalprogect_shift.domain.usecases.auth.CheckRegistrationUseCase
import com.example.finalprogect_shift.presentation.BaseStates
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.HttpException

class SignInViewModel(
    private val checkRegistrationUseCase: CheckRegistrationUseCase,
    private val storage: UserPreferencesStorage,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    private val _state: MutableLiveData<BaseStates<ResponseBody>> = MutableLiveData()
    val state: LiveData<BaseStates<ResponseBody>> = _state

    private val ioScope = CoroutineScope(ioDispatcher)

    fun onSignInBtnClick(name: String, password: String) {
        if (name.isEmpty() || password.isEmpty()) {
            _state.value = BaseStates.EmptyField
        } else {
            _state.value = BaseStates.FilledField
            viewModelScope.launch {
                doOnBackground(name, password)
            }
        }
    }

    fun setToken(token: String) = viewModelScope.launch {
        storage.saveToken(token)
    }

    private suspend fun doOnBackground(name: String, password: String) {
        try {
            _state.value = BaseStates.Loading
            _state.value = withContext(ioDispatcher){
                async {
                    return@async BaseStates.SuccessResponseBody(checkRegistrationUseCase(name, password))
                }.await()
            }
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