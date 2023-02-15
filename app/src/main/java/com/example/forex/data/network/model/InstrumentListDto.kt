package com.example.forex.data.network.model

data class InstrumentListDto(
    val currencies: Map<String, String>?,
    val success: Boolean
)
