package com.example.finalprogect_shift.presentation.getloan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprogect_shift.domain.usecases.loan.GetLoanUseCase
import javax.inject.Inject

class GetLoanViewModelFactory @Inject constructor(
    private val getLoanUseCase: GetLoanUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GetLoanViewModel(getLoanUseCase) as T
    }
}