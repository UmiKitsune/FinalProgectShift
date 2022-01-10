package com.example.finalprogect_shift.data.api.loan

import com.example.finalprogect_shift.data.models.LoanConditionsData
import retrofit2.http.GET
import retrofit2.http.Header

interface LoanConditionsApi {

   @GET("loans/conditions")
   suspend fun getLoanConditions(@Header("Authorization") authToken: String): LoanConditionsData
}