package com.example.forex.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "instrument_symbol",
    primaryKeys = ["code"],
    indices = [
        Index(value = ["code"])
    ]
)
data class InstrumentSymbolEntity (
    @ColumnInfo(name = "code")
    val symbol:String
)