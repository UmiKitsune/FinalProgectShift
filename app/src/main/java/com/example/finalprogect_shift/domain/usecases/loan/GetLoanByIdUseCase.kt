package com.example.finalprogect_shift.domain.usecases.loan

import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.domain.repositories.LoanRepository
import javax.inject.Inject

class GetLoanByIdUseCase @Inject constructor(private val repository: LoanRepository) {
    suspend operator fun invoke(token: String, id: Int): GetLoanResponse =
        repository.getLoanById(token, id)
}