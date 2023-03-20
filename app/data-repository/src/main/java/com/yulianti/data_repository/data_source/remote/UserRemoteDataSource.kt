package com.yulianti.data_repository.data_source.remote

import com.example.forex.com.yulianti.mynewmodule.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    fun getUserFromRemote(id: String): Flow<User>
    fun getUsers(): Flow<List<User>>
}