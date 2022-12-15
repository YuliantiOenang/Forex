package com.example.seekercapitaltest.data.network.model

data class InstrumentLiveDto(
    val source: String,
    val quotes: Map<String, Float>?,
    val timestamp: Int,
    val success: Boolean
)
