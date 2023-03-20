package com.yulianti.data_repository.data_source.local

import com.example.forex.com.yulianti.mynewmodule.entity.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun insertUser(user: User)
    suspend fun addUser(users: List<User>)
    fun getUser(userId: String): Flow<User>
    fun getUsers(): Flow<List<User>>
}