package com.example.finalprogect_shift.data.api.loan

import com.example.finalprogect_shift.data.models.GetLoanRequestData
import com.example.finalprogect_shift.data.models.GetLoanResponseData
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GetLoanApi {
    @POST("loans")
    suspend fun getLoan(
        @Header("Authorization") authToken: String,
        @Body getLoanRequestData: GetLoanRequestData
    ): GetLoanResponseData
}