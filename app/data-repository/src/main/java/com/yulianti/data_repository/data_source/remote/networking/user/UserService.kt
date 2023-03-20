package com.yulianti.data_repository.data_source.remote.networking.user

import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/users")
    suspend fun getUsers(): List<UserApiModel>

    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): UserApiModel

}