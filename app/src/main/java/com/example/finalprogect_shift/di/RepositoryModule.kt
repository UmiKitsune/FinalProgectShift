package com.example.finalprogect_shift.di

import com.example.finalprogect_shift.data.repositories.LoanRepositoryImpl
import com.example.finalprogect_shift.data.repositories.RegistrationRepositoryImpl
import com.example.finalprogect_shift.domain.repositories.LoanRepository
import com.example.finalprogect_shift.domain.repositories.RegistrationRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRegistrationRepository(impl: RegistrationRepositoryImpl): RegistrationRepository

    @Binds
    @Singleton
    fun bindLoanRepository(impl: LoanRepositoryImpl): LoanRepository
}