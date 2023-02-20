package com.example.forex.data.network

import com.example.forex.core.network.model.NetworkChangeList
import com.example.forex.data.network.model.InstrumentLiveDto
import com.example.forex.data.network.model.InstrumentListDto

interface InstrumentDataSource {
    suspend fun getInstrumentLive(from: String, to:String): InstrumentLiveDto
    suspend fun getInstrumentList(): InstrumentListDto

    suspend fun getMarketChangeList(after: Int? = null): List<NetworkChangeList>
}