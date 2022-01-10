package com.example.finalprogect_shift.di

import com.example.finalprogect_shift.data.api.auth.SignInApi
import com.example.finalprogect_shift.data.api.auth.SignUpApi
import com.example.finalprogect_shift.data.api.loan.AllLoansApi
import com.example.finalprogect_shift.data.api.loan.GetLoanApi
import com.example.finalprogect_shift.data.api.loan.LoanByIdApi
import com.example.finalprogect_shift.data.api.loan.LoanConditionsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideSignInApi(retrofit: Retrofit): SignInApi =
        retrofit.create(SignInApi::class.java)

    @Provides
    @Singleton
    fun provideSignUpApi(retrofit: Retrofit): SignUpApi =
        retrofit.create(SignUpApi::class.java)

    @Provides
    @Singleton
    fun provideLoadConditionApi(retrofit: Retrofit): LoanConditionsApi =
        retrofit.create(LoanConditionsApi::class.java)

    @Provides
    @Singleton
    fun provideGetLoanApi(retrofit: Retrofit): GetLoanApi =
        retrofit.create(GetLoanApi::class.java)

    @Provides
    @Singleton
    fun provideGetAllLoanInfoApi(retrofit: Retrofit): AllLoansApi =
        retrofit.create(AllLoansApi::class.java)

    @Provides
    @Singleton
    fun provideGetLoanByIDApi(retrofit: Retrofit): LoanByIdApi =
        retrofit.create(LoanByIdApi::class.java)
}