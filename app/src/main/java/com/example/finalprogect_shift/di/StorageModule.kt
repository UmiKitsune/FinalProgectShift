package com.example.finalprogect_shift.di

import android.content.Context
import com.example.finalprogect_shift.data.storage.UserPreferencesStorage
import dagger.Module
import dagger.Provides

@Module
class StorageModule( private val context: Context) {

    @Provides
    fun provideStorage(): UserPreferencesStorage {
        return UserPreferencesStorage(context.applicationContext)
    }
}