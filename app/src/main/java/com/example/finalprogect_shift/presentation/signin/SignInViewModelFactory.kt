package com.example.finalprogect_shift.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprogect_shift.data.storage.UserPreferencesStorage
import com.example.finalprogect_shift.domain.usecases.auth.CheckRegistrationUseCase
import javax.inject.Inject

class SignInViewModelFactory @Inject constructor(
    private val checkRegistrationUseCase: CheckRegistrationUseCase,
    private val storage: UserPreferencesStorage
): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(checkRegistrationUseCase, storage) as T
    }
}