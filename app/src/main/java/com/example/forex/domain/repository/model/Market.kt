package com.example.forex.domain.repository.model

data class Market(
    val account: Account,
    val listMarket: List<Instrument>
)
