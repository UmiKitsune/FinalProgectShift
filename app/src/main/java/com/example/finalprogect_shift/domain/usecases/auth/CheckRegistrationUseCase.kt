package com.example.finalprogect_shift.domain.usecases.auth

import com.example.finalprogect_shift.domain.repositories.RegistrationRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class CheckRegistrationUseCase @Inject constructor(private val repository: RegistrationRepository) {

    suspend operator fun invoke(name: String, password: String): ResponseBody =
        repository.checkRegistration(name, password)
}