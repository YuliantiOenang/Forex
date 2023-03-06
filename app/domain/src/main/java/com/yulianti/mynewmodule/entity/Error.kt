package com.example.forex.com.yulianti.mynewmodule.entity

sealed class Error(override val cause: Throwable?): Throwable(cause) {
    class PostException(cause: Throwable?) : Error(cause)
    class UserException(cause: Throwable?) : Error(cause)
    class UnknownException(cause: Throwable?) : Error(cause)
    companion object {
        fun createThrowableError(throwable: Throwable): Error {
            return if (throwable is Error) return throwable else UnknownException(throwable)
        }
    }
}