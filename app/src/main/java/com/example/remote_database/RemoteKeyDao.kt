package com.example.remote_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.QuoteRemoteKeys

@Dao
interface RemoteKeyDao {

    @Query("select * from QuoteRemoteKeys where id =:id")
    suspend fun getRemoteKeys(id:String):QuoteRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKeys: List<QuoteRemoteKeys>)

    @Query("delete from QuoteRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}