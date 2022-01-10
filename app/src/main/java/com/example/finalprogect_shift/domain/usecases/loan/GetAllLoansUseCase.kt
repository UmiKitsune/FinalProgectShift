package com.example.finalprogect_shift.domain.usecases.loan

import com.example.finalprogect_shift.domain.models.ShowLoansList
import com.example.finalprogect_shift.domain.repositories.LoanRepository
import javax.inject.Inject

class GetAllLoansUseCase@Inject constructor(private val repository: LoanRepository) {
    suspend operator fun invoke(token: String): ShowLoansList =
        repository.getAllLoans(token)
}