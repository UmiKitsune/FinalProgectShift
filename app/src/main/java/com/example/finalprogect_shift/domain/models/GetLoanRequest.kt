package com.example.finalprogect_shift.domain.models

data class GetLoanRequest(
    val amount: Int,
    val percent: Double,
    val period: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)