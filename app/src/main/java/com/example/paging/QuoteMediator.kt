package com.example.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.model.QuoteRemoteKeys
import com.example.model.Result
import com.example.remote_database.QuoteDatabase
import com.example.web_services.QuoteServices


@OptIn(ExperimentalPagingApi::class)
class QuoteMediator(private val quoteServices: QuoteServices,
                    private val quoteDatabase: QuoteDatabase)
    :RemoteMediator<Int, com.example.model.Result>() {

    val quoteDAO = quoteDatabase.getQuoteDao()
    val quoteRemoteKeyDao = quoteDatabase.getRemoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
         return try {
             val currentPage = when(loadType){
                 LoadType.REFRESH->{
                     val remoteKeys = getRemoteKeyForClosestToCurrentPosition(state)
                     remoteKeys?.nextPage?.minus(1) ?: 1
                 }
                 LoadType.PREPEND->{
                     val remoteKeys = getRemoteKeyForFirstItem(state)
                     val prevPage = remoteKeys?.prevPage
                         ?: return MediatorResult
                             .Success(endOfPaginationReached = remoteKeys!= null)
                     prevPage
                 }
                 LoadType.APPEND->{
                     val  remoteKeys = getRemoteKeyForLastItem(state)
                     val nextPage = remoteKeys?.nextPage
                         ?: return MediatorResult
                             .Success(endOfPaginationReached = remoteKeys !=null)
                     nextPage
                 }
             }

             val response = quoteServices.getQuotes(currentPage)
             val endOfPaginationReached = response.totalPages == currentPage

             val prevPage = if(currentPage == 1) null else currentPage-1
             val nextPage = if(endOfPaginationReached) null else currentPage+1

             quoteDatabase.withTransaction {
                 if(loadType == LoadType.REFRESH){
                     quoteDAO.deleteQuotes()
                     quoteRemoteKeyDao.deleteAllRemoteKeys()
                 }
                 quoteDAO.addQuotes(response.results)
                 val keys = response.results.map{quote->
                     QuoteRemoteKeys(
                         id = quote._id,
                         prevPage = prevPage,
                         nextPage = nextPage
                     )
                  }
                 quoteRemoteKeyDao.addRemoteKeys(keys)
             }
             MediatorResult.Success(endOfPaginationReached)
        }
         catch (error :Exception){
             MediatorResult.Error(error)
         }
    }

    private suspend fun getRemoteKeyForClosestToCurrentPosition(state: PagingState<Int, Result>): QuoteRemoteKeys?{
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?._id?.let {id->
                quoteRemoteKeyDao.getRemoteKeys(id = id)
            }
        }

    }

    private suspend fun getRemoteKeyForFirstItem(state:PagingState<Int,com.example.model.Result>):QuoteRemoteKeys?{
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { quote->
            quoteRemoteKeyDao.getRemoteKeys(id= quote._id)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state:PagingState<Int,com.example.model.Result>):QuoteRemoteKeys?{
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {quote->
            quoteRemoteKeyDao.getRemoteKeys(id = quote._id)
        }
    }
}