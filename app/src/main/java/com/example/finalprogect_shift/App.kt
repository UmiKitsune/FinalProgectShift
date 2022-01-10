package com.example.finalprogect_shift

import android.app.Application
import com.example.finalprogect_shift.di.AppComponent
import com.example.finalprogect_shift.di.DaggerAppComponent
import com.example.finalprogect_shift.di.StorageModule


class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().storageModule(StorageModule(this)).build()

    }

}