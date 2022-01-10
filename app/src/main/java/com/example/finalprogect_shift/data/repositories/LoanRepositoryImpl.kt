package com.example.finalprogect_shift.data.repositories

import com.example.finalprogect_shift.data.api.loan.AllLoansApi
import com.example.finalprogect_shift.data.api.loan.GetLoanApi
import com.example.finalprogect_shift.data.api.loan.LoanByIdApi
import com.example.finalprogect_shift.data.api.loan.LoanConditionsApi
import com.example.finalprogect_shift.data.mappers.Mapper
import com.example.finalprogect_shift.data.models.ShowLoansListData
import com.example.finalprogect_shift.domain.models.GetLoanRequest
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.domain.models.LoanConditions
import com.example.finalprogect_shift.domain.models.ShowLoansList
import com.example.finalprogect_shift.domain.repositories.LoanRepository
import javax.inject.Inject

class LoanRepositoryImpl@Inject constructor(
    private val getLoanApi: GetLoanApi,
    private val loanConditionsApi: LoanConditionsApi,
    private val allLoansApi: AllLoansApi,
    private val loanByIdApi: LoanByIdApi
): LoanRepository {

    override suspend fun getLoanConditions(token: String): LoanConditions =
       Mapper.fromLoanConditionData(loanConditionsApi.getLoanConditions(token))


    override suspend fun getLoan(token: String, loanRequest: GetLoanRequest): GetLoanResponse =
        Mapper.fromLoanResponseData(getLoanApi.getLoan(token, Mapper.toLoanRequestData(loanRequest)))

    override suspend fun getAllLoans(token: String): ShowLoansList =
        Mapper.fromShowLoansListData(ShowLoansListData(allLoansApi.getAllLoansInfo(token)))

    override suspend fun getLoanById(token: String, id: Int): GetLoanResponse =
        Mapper.fromLoanResponseData(loanByIdApi.getLoanById(token, id))
}