package com.example.seekercapitaltest.data.network.retrofit

import com.example.seekercapitaltest.data.network.model.InstrumentLiveDto
import com.example.seekercapitaltest.data.network.model.InstrumentListDto
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