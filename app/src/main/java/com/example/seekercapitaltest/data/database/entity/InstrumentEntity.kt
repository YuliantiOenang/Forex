package com.example.seekercapitaltest.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "instrument",
    primaryKeys = ["symbol"],
    indices = [
        Index(value = ["symbol"])
    ]
)
data class InstrumentEntity (
    @ColumnInfo(name = "symbol")
    val symbol:String,
    @ColumnInfo(name = "original_price")
    val original_price:Float,
    @ColumnInfo(name = "sell")
    val sell:Float,
    @ColumnInfo(name = "buy")
    val buy:Float
)