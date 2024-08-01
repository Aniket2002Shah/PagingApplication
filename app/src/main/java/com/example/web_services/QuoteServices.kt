package com.example.web_services

import com.example.model.QuoteList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteServices {

    @GET("/quotes")
    suspend fun getQuotes(@Query("page")page:Int):QuoteList
}