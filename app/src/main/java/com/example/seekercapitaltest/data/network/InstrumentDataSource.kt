package com.example.seekercapitaltest.data.network

import com.example.seekercapitaltest.data.network.model.InstrumentLiveDto
import com.example.seekercapitaltest.data.network.model.InstrumentListDto

interface InstrumentDataSource {
    suspend fun getInstrumentLive(from: String, to:String): InstrumentLiveDto
    suspend fun getInstrumentList(): InstrumentListDto
}