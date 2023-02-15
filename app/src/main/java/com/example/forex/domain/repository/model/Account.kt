package com.example.forex.domain.repository.model

data class Account (
    var equity: Float,
    var balance: Float,
    var margin: Float,
    var used: Float
)