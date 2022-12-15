package com.example.seekercapitaltest.domain.usecase

import com.example.seekercapitaltest.common.Resource
import com.example.seekercapitaltest.domain.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class DeleteDbUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            repository.deleteDb()
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}