package com.example.remote_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.model.QuoteRemoteKeys

@Database(entities = [com.example.model.Result::class,QuoteRemoteKeys::class], version = 1)
abstract class QuoteDatabase :RoomDatabase(){

    abstract fun getQuoteDao():QuoteDao
    abstract fun getRemoteKeyDao():RemoteKeyDao
}