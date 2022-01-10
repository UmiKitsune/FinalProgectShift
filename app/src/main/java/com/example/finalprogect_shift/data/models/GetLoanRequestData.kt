package com.example.finalprogect_shift.data.models

data class GetLoanRequestData(
    val amount: Int,
    val percent: Double,
    val period: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)