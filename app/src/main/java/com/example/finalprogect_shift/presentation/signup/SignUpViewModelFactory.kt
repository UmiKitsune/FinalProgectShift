package com.example.finalprogect_shift.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprogect_shift.domain.usecases.auth.RegistrationUseCase
import javax.inject.Inject

class SignUpViewModelFactory @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(registrationUseCase) as T
    }
}