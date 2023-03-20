package com.yulianti.data_repository.repository

import com.example.forex.com.yulianti.mynewmodule.entity.User
import com.yulianti.data_repository.data_source.local.UserLocalDataSource
import com.yulianti.data_repository.data_source.remote.UserRemoteDataSource
import kotlinx.coroutines.flow.*
import repository.UserRepository

class UserRepositoryImpl(val remoteDataSource: UserRemoteDataSource, val localDataStore: UserLocalDataSource): UserRepository {
    private val userFlows = MutableStateFlow(emptyMap<String, User>().toMutableMap())

    override fun getUser(userId: String): Flow<User> = userFlows.flatMapLatest {
            val user = it[userId]
            if (user != null) {
                flowOf(user)
            } else {
                localDataStore.getUser(userId).onEach { user ->
                    saveUser(user)
                }
            }
        }

    override fun refreshUser(userId: String): Flow<User> {
        return remoteDataSource.getUserFromRemote(userId).onEach { localDataStore.insertUser(it) }
    }

    override fun getUsers(): Flow<List<User>> {
        return remoteDataSource.getUsers().onEach {
            localDataStore.addUser(it)
        }
    }

    private fun saveUser(user: User) {
        val map = userFlows.value
        map[user.id] = user
        userFlows.value = map
    }
}