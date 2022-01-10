package com.example.finalprogect_shift.data.models

import java.io.Serializable
import java.util.*


data class GetLoanResponseData(
    val amount: Int,
    val percent: Double,
    val period: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val date: Date,
    val state: String,
    val id: Int
): Serializable

class ShowLoansListData(val loans: List<GetLoanResponseData>): Serializable
