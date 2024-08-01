package com.example.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.Result
import com.example.web_services.QuoteServices


class QuotePagingSource(private val quoteServices: QuoteServices): PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
       return state.anchorPosition?.let {
           state.closestPageToPosition(it)?.prevKey?.plus(1)
               ?:state.closestPageToPosition(it)?.nextKey?.minus(1)
       }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val startpoint = 1
            val position = params.key ?: startpoint
            val response = quoteServices.getQuotes(position)
            LoadResult.Page(
                data = response.results,
                prevKey =if(position==1) null else position-1,
                nextKey = if (position==response.totalPages) null else position+1
            )
        }
        catch (e:Exception){
            Log.d("Shingekinokyojin",e.message.toString())
            LoadResult.Error(e)
        }
    }
}
