package com.example.finalprogect_shift.data.api.loan

import com.example.finalprogect_shift.data.models.GetLoanResponseData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LoanByIdApi {
    @GET("loans/{id}")
    suspend fun getLoanById(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): GetLoanResponseData
}