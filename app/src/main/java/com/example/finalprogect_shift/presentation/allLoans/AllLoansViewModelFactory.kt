package com.example.finalprogect_shift.presentation.allLoans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprogect_shift.domain.usecases.loan.GetLoanByIdUseCase
import javax.inject.Inject

class AllLoansViewModelFactory @Inject constructor(
    private val loanByIdUseCase: GetLoanByIdUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AllLoansViewModel(loanByIdUseCase) as T
    }
}