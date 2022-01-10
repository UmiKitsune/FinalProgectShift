package com.example.finalprogect_shift.di

import com.example.finalprogect_shift.ui.MainActivity
import com.example.finalprogect_shift.ui.fragments.MainFragment
import com.example.finalprogect_shift.ui.fragments.auth.SignInFragment
import com.example.finalprogect_shift.ui.fragments.auth.SignUpFragment
import com.example.finalprogect_shift.ui.fragments.loan.AllLoansFragment
import com.example.finalprogect_shift.ui.fragments.loan.GetLoanFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AppModule::class,
    ApiModule::class,
    RepositoryModule::class,
    StorageModule::class
))
interface AppComponent {

    fun inject(fragment: SignInFragment)

    fun inject(fragment: SignUpFragment)

    fun inject(fragment: MainFragment)

    fun inject(fragment: GetLoanFragment)

    fun inject(fragment: AllLoansFragment)

    fun inject(activity: MainActivity)
}