package com.example.forex.domain.repository

import com.example.forex.core.Syncable
import com.example.forex.domain.repository.model.Account
import com.example.forex.domain.repository.model.Instrument
import com.example.forex.domain.repository.model.InstrumentCode

interface MarketRepository : Syncable {
    suspend fun getInstrumentList(): List<InstrumentCode>
    suspend fun getInstrumentLive(from: String, to: String): List<Instrument>
    fun getAccount(instruments: List<Instrument>): Account
    suspend fun deleteDb()
}