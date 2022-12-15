package com.example.seekercapitaltest.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seekercapitaltest.data.database.entity.InstrumentSymbolEntity

@Dao
interface InstrumentSymbolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(instrument: List<InstrumentSymbolEntity>)

    @Query("SELECT * FROM instrument_symbol")
    fun getAll(): List<InstrumentSymbolEntity>

    @Query(value = "DELETE FROM instrument_symbol")
    fun deleteAll(): Int
}