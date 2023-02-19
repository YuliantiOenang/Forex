package com.example.forex.domain.usecase

import android.util.Log
import com.example.forex.common.Resource
import com.example.forex.domain.repository.MarketRepository
import com.example.forex.domain.repository.model.Market
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class GetMarketListUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    operator fun invoke(from: String, to: String?): Flow<Resource<Market>> = flow {
        while (true) {
            try {
                emit(Resource.Loading())
                val stringTo = if (to.isNullOrEmpty()) {
                    val toCode = repository.getInstrumentList()
                    val toString = mutableListOf<String>()
                    toCode.forEach { toString.add(it.symbol) }
                    toString.joinToString(", ")
                } else {
                    to
                }
                if (stringTo.isEmpty()) {
                    emit(Resource.Error("An unexpected error occured"))
                } else {
                    val instrumentList = repository.getInstrumentLive(from, stringTo)
                    Log.d("yuli", instrumentList.toString())
                    val account = repository.getAccount(instrumentList)
                    emit(Resource.Success(Market(account, instrumentList)))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
            delay(5000)
        }
    }
}