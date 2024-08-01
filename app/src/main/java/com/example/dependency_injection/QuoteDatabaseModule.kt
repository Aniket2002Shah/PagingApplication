package com.example.dependency_injection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remote_database.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class QuoteDatabaseModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context):QuoteDatabase{
        return Room.databaseBuilder(context, QuoteDatabase::class.java,"quoteDB").build()
    }
}