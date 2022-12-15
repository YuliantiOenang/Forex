package com.example.seekercapitaltest.data.network.di

import com.example.seekercapitaltest.data.network.InstrumentDataSource
import com.example.seekercapitaltest.data.network.retrofit.InstrumentNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindInstrumentNetwork(instrumentNetwork: InstrumentNetwork): InstrumentDataSource
}