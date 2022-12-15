package com.example.seekercapitaltest.domain.usecase

import com.example.seekercapitaltest.common.Resource
import com.example.seekercapitaltest.domain.repository.MarketRepository
import com.example.seekercapitaltest.domain.repository.model.Market
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class GetMarketListUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    operator fun invoke(from: String, to: String?): Flow<Resource<Market>> = flow {
        try {
            emit(Resource.Loading())
            val stringTo = if (to.isNullOrEmpty()) {
                val toCode = repository.getInstrumentList()
                val toString = mutableListOf<String>()
                toCode.forEach{toString.add(it.symbol)}
                toString.joinToString(", ")
            } else {
                to
            }
            if (stringTo.isEmpty()) {
                emit(Resource.Error("An unexpected error occured"))
            } else {
                val instrumentList = repository.getInstrumentLive(from, stringTo)
                val account = repository.getAccount(instrumentList)
                emit(Resource.Success(Market(account, instrumentList)))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}