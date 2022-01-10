package com.example.finalprogect_shift.domain.repositories

import com.example.finalprogect_shift.domain.models.GetLoanRequest
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.domain.models.LoanConditions
import com.example.finalprogect_shift.domain.models.ShowLoansList

interface LoanRepository {

    suspend fun getLoanConditions(token: String): LoanConditions

    suspend fun getLoan(token: String, loanRequest: GetLoanRequest): GetLoanResponse

    suspend fun getAllLoans(token: String): ShowLoansList

    suspend fun getLoanById(token: String, id: Int): GetLoanResponse
}