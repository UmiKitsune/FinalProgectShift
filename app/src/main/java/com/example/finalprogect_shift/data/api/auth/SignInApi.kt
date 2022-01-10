package com.example.finalprogect_shift.data.api.auth


import com.example.finalprogect_shift.data.models.UserData
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInApi {

    @POST("login")
    suspend fun checkUserRegistration(@Body userData: UserData): ResponseBody
}