package com.example.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.paging.QuoteMediator
import com.example.paging.QuotePagingSource
import com.example.remote_database.QuoteDatabase
import com.example.web_services.QuoteServices
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val services: QuoteServices, private  val quoteDatabase: QuoteDatabase) {

    //Direct Networking-----------
//    fun getQuotes()= Pager(
//        config = PagingConfig(pageSize = 20, maxSize = 100),
//        pagingSourceFactory ={QuotePagingSource(services)}
//    ).liveData

    //Networking through Sql local catching---------
    @OptIn(ExperimentalPagingApi::class)
    fun getQuotes()= Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator =QuoteMediator(services,quoteDatabase) ,
        pagingSourceFactory ={quoteDatabase.getQuoteDao().getQuotes()}
    ).liveData
}