package com.example.remote_database

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface QuoteDao {
    @Query("select * from Result")
    fun getQuotes():PagingSource<Int, com.example.model.Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(list:List<com.example.model.Result>)

    @Query("delete from Result")
    suspend fun deleteQuotes()

}