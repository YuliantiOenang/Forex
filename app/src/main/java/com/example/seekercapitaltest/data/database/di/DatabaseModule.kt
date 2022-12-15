package com.example.seekercapitaltest.data.database.di

import android.content.Context
import androidx.room.Room
import com.example.seekercapitaltest.data.database.databases.SeekerCapitalTestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SeekerCapitalTestDatabase {
        return Room.databaseBuilder(
            context,
            SeekerCapitalTestDatabase::class.java,
            "seekercapitaltest-databases"
        ).build()
    }
}