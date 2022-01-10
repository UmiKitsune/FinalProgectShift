package com.example.finalprogect_shift.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprogect_shift.domain.usecases.loan.GetAllLoansUseCase
import com.example.finalprogect_shift.domain.usecases.loan.GetLoanConditionUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val getLoanConditionUseCase: GetLoanConditionUseCase,
    private val getAllLoansUseCase: GetAllLoansUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getLoanConditionUseCase, getAllLoansUseCase) as T
    }
}