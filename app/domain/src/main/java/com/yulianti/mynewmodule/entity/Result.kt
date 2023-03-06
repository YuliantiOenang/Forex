package com.example.forex.com.yulianti.mynewmodule.entity

sealed class Result<T: Any> {
    data class Success<T: Any>(val data: T): Result<T>()
    class Failed(val exception: Error): Result<Nothing>()
}
