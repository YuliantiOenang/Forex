package com.example.forex.usecase

import com.example.forex.com.yulianti.mynewmodule.entity.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

abstract class usecase<I : usecase.Input, O : usecase.Output>(private val configuration: Configuration) {
    fun execute(request: I) = process(request).flowOn(configuration.dispatcher).map {
        com.example.forex.com.yulianti.mynewmodule.entity.Result.Success(it) as Result<O>
    }.catch {
        emit(
            com.example.forex.com.yulianti.mynewmodule.entity.Result.Failed(
                Error.createThrowableError(
                    it
                )
            ) as Result<O>
        )
    }

    internal abstract fun process(request: I): Flow<O>

    class Configuration(val dispatcher: CoroutineDispatcher)

    interface Input
    interface Output
}