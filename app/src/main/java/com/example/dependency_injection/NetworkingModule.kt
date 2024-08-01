package com.example.dependency_injection

import com.example.utils.Constants
import com.example.web_services.QuoteServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkingModule {

    @Singleton
    @Provides
    fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun getQuoteServices(retrofit: Retrofit):QuoteServices{
        return retrofit.create(QuoteServices::class.java)
    }
}