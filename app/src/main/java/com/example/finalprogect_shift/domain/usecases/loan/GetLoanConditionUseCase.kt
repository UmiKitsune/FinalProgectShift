package com.example.finalprogect_shift.domain.usecases.loan

import com.example.finalprogect_shift.domain.models.LoanConditions
import com.example.finalprogect_shift.domain.repositories.LoanRepository
import javax.inject.Inject

class GetLoanConditionUseCase @Inject constructor(private val repository: LoanRepository) {
    suspend operator fun invoke(token: String): LoanConditions =
        repository.getLoanConditions(token)
}

