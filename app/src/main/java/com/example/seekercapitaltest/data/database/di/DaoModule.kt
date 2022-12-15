package com.example.seekercapitaltest.data.database.di

import com.example.seekercapitaltest.data.database.dao.InstrumentDao
import com.example.seekercapitaltest.data.database.dao.InstrumentSymbolDao
import com.example.seekercapitaltest.data.database.databases.SeekerCapitalTestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideInstrumentDao(database: SeekerCapitalTestDatabase) : InstrumentDao {
        return database.instrumentDao()
    }

    @Provides
    fun provideInstrumentSymbolDao(database: SeekerCapitalTestDatabase) : InstrumentSymbolDao {
        return database.instrumentSymbolDao()
    }
}