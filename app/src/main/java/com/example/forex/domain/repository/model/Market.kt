package com.example.forex.domain.repository.model

import android.util.Log
import java.util.*

data class Market(
    val account: Account,
    val listMarket: List<Instrument>


) {
    override fun equals(other: Any?): Boolean {
        Log.d("yuli", "equals called")
        return false
    }

    override fun hashCode(): Int {
        Log.d("yuli", "hashCode:"+super.hashCode())
        return super.hashCode()
    }
}
