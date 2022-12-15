package com.example.seekercapitaltest.domain.repository.model

data class Instrument (
    var symbol: String,
    var originalPrice: Float,
    var change: Float,
    var sell: Float,
    var buy: Float
)