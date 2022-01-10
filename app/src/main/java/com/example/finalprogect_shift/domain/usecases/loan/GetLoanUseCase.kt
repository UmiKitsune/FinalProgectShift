package com.example.finalprogect_shift.domain.usecases.loan

import com.example.finalprogect_shift.domain.models.GetLoanRequest
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.domain.repositories.LoanRepository
import javax.inject.Inject

class GetLoanUseCase @Inject constructor(private val repository: LoanRepository) {
    suspend operator fun invoke(token: String, loanRequest: GetLoanRequest): GetLoanResponse =
        repository.getLoan(token, loanRequest)
}