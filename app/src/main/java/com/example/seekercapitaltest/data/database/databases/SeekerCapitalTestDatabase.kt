package com.example.seekercapitaltest.data.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.seekercapitaltest.data.database.dao.InstrumentDao
import com.example.seekercapitaltest.data.database.dao.InstrumentSymbolDao
import com.example.seekercapitaltest.data.database.entity.InstrumentEntity
import com.example.seekercapitaltest.data.database.entity.InstrumentSymbolEntity

@Database(entities = [InstrumentEntity::class, InstrumentSymbolEntity::class], version = 1)
abstract class SeekerCapitalTestDatabase: RoomDatabase() {
    abstract fun instrumentDao(): InstrumentDao
    abstract fun instrumentSymbolDao(): InstrumentSymbolDao
}