package com.example.seekercapitaltest.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.seekercapitaltest.data.database.entity.InstrumentEntity
import io.reactivex.Observable

@Dao
interface InstrumentDao  {
    @Insert(onConflict = REPLACE)
    fun insertAll(instrument: List<InstrumentEntity>)

    @Insert(onConflict = REPLACE)
    fun insert(instrument: InstrumentEntity)

    @Query("SELECT * FROM instrument")
    fun getAll(): List<InstrumentEntity>

    @Query("SELECT * FROM instrument WHERE symbol in (:id)")
    fun getById(id: String): List<InstrumentEntity>

    @Query("SELECT * FROM instrument")
    fun getAllObservable(): Observable<List<InstrumentEntity>>

    @Query(value = "DELETE FROM instrument WHERE symbol in (:instrument)")
    fun delete(instrument: List<String>)

    @Delete
    fun deleteById(list: List<InstrumentEntity>)

    @Query(value = "DELETE FROM instrument")
    fun deleteAll(): Int
}