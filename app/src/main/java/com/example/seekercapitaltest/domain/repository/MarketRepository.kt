package com.example.seekercapitaltest.domain.repository

import com.example.seekercapitaltest.domain.repository.model.Account
import com.example.seekercapitaltest.domain.repository.model.Instrument
import com.example.seekercapitaltest.domain.repository.model.InstrumentCode

interface MarketRepository {
    suspend fun getInstrumentList(): List<InstrumentCode>
    suspend fun getInstrumentLive(from: String, to: String): List<Instrument>
    fun getAccount(instruments: List<Instrument>): Account
    suspend fun deleteDb()
}