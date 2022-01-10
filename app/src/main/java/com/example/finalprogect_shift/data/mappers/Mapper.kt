package com.example.finalprogect_shift.data.mappers

import com.example.finalprogect_shift.data.models.GetLoanRequestData
import com.example.finalprogect_shift.data.models.GetLoanResponseData
import com.example.finalprogect_shift.data.models.LoanConditionsData
import com.example.finalprogect_shift.data.models.ShowLoansListData
import com.example.finalprogect_shift.domain.models.GetLoanRequest
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.domain.models.LoanConditions
import com.example.finalprogect_shift.domain.models.ShowLoansList

class Mapper {

    companion object {

        fun fromLoanConditionData(loanConditionData: LoanConditionsData): LoanConditions =
            LoanConditions(
                maxAmount = loanConditionData.maxAmount,
                percent = loanConditionData.percent,
                period = loanConditionData.period
            )

        fun fromLoanResponseData(loanResponseData: GetLoanResponseData): GetLoanResponse =
            GetLoanResponse(
                amount = loanResponseData.amount,
                percent = loanResponseData.percent,
                period = loanResponseData.period,
                firstName = loanResponseData.firstName,
                lastName = loanResponseData.lastName,
                phoneNumber = loanResponseData.phoneNumber,
                date = loanResponseData.date,
                state = loanResponseData.state,
                id = loanResponseData.id
            )

        fun toLoanRequestData(loanRequest: GetLoanRequest): GetLoanRequestData =
            GetLoanRequestData(
                amount = loanRequest.amount,
                percent = loanRequest.percent,
                period = loanRequest.period,
                firstName = loanRequest.firstName,
                lastName = loanRequest.lastName,
                phoneNumber = loanRequest.phoneNumber
            )

        fun fromShowLoansListData(loansListData: ShowLoansListData): ShowLoansList {
            val loanList = loansListData.loans.map {
                fromLoanResponseData(it)
            }
            return ShowLoansList(loanList)
        }

    }
}