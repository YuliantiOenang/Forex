package com.example.forex.domain.repository.di

import com.example.forex.domain.repository.MarketRepositoryImpl
import com.example.forex.domain.repository.MarketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindInstrumentRepository(instrumentRepository: MarketRepositoryImpl): MarketRepository
}