package com.example.finalprogect_shift.domain.repositories

import okhttp3.ResponseBody

interface RegistrationRepository {

    suspend fun checkRegistration(name: String, password: String): ResponseBody

    suspend fun registration(name: String, password: String): ResponseBody

}