package com.example.forex.data.network.retrofit

import com.example.forex.data.network.model.InstrumentLiveDto
import com.example.forex.data.network.model.InstrumentListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface InstrumentApi {
    @GET("live")
    suspend fun getInstrumentLive(
        @Query("source") source: String,
        @Query("currencies") currency: String
    ): InstrumentLiveDto

    @GET("list")
    suspend fun getInstrumentList(): InstrumentListDto
}