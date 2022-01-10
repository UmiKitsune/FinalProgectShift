package com.example.finalprogect_shift.di

import com.example.finalprogect_shift.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    private const val BASE_URL = "https://focusstart.appspot.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().also { client ->
                    if (BuildConfig.DEBUG) {
                        val log = HttpLoggingInterceptor()
                        log.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(log)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}