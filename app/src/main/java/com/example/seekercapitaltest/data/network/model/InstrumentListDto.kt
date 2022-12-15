package com.example.seekercapitaltest.data.network.model

data class InstrumentListDto(
    val currencies: Map<String, String>?,
    val success: Boolean
)
