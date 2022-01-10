package com.example.finalprogect_shift.data.repositories

import com.example.finalprogect_shift.data.api.auth.SignInApi
import com.example.finalprogect_shift.data.api.auth.SignUpApi
import com.example.finalprogect_shift.data.models.UserData
import com.example.finalprogect_shift.domain.repositories.RegistrationRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val signInApi: SignInApi,
    private val signUpApi: SignUpApi,
        ): RegistrationRepository {

    override suspend fun checkRegistration(name: String, password: String): ResponseBody =
        signInApi.checkUserRegistration(UserData(name, password))


    override suspend fun registration(name: String, password: String): ResponseBody =
        signUpApi.userRegistration(UserData(name, password))

}