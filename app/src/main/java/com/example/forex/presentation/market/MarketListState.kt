package com.example.forex.presentation.market

import com.example.forex.domain.repository.model.Account
import com.example.forex.domain.repository.model.Market

data class MarketListState(
    val isLoading: Boolean = false,
    var market: Market = Market(Account(0.0f,0.0f,0.0f,0.0f), emptyList()),
    val error: String = ""
)
