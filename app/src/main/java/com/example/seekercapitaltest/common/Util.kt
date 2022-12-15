package com.example.seekercapitaltest.common

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class Util {
    companion object{
        fun formatCurrency(money: Float, currency: String = "USD"): String {
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 2
            format.currency = Currency.getInstance(currency)
            return format.format(money)
        }

        fun roundOffDecimal(number: Float): Float {
            val df = DecimalFormat("#.##;#.##")
            df.roundingMode = RoundingMode.CEILING
            val formattedValue = df.format(number)
            return formattedValue.toFloat()
        }
    }
}