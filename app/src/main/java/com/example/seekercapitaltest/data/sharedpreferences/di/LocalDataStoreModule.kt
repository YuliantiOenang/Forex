package com.example.seekercapitaltest.data.sharedpreferences.di

import com.example.seekercapitaltest.data.sharedpreferences.SharedPreferenceLocalDataStore
import com.example.seekercapitaltest.data.sharedpreferences.SeekerCapitalTestLocalDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataStoreModule {
    @Binds
    fun bindCurrencyExchangeLocalDataStore(sharedPreferenceLocalDataStore: SeekerCapitalTestLocalDataStore): SharedPreferenceLocalDataStore
}