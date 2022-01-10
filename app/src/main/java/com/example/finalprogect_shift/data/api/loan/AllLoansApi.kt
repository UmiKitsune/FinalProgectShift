package com.example.finalprogect_shift.data.api.loan

import com.example.finalprogect_shift.data.models.GetLoanResponseData
import retrofit2.http.GET
import retrofit2.http.Header

interface AllLoansApi {
    @GET("loans/all")
    suspend fun getAllLoansInfo(@Header("Authorization") authToken: String): List<GetLoanResponseData>
}