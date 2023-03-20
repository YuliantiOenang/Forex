package com.yulianti.data_repository.data_source.local.source

import com.example.forex.com.yulianti.mynewmodule.entity.User
import com.yulianti.data_repository.data_source.local.UserLocalDataSource
import com.yulianti.data_repository.data_source.local.db.user.UserDao
import com.yulianti.data_repository.data_source.local.db.user.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {
    override suspend fun insertUser(user: User) {
        userDao.insertUsers(listOf(UserEntity(user.id.toLong(), user.name, user.username, user.email)))
    }

    override suspend fun addUser(users: List<User>) {
        userDao.insertUsers(users.map { user ->
            UserEntity(
                user.id.toLong(),
                user.name,
                user.username,
                user.email
            )
        })
    }

    override fun getUser(userId: String) = userDao.getUser(userId.toLong())
        .map { User(it.id.toString(), it.name, it.username, it.email) }

    override fun getUsers(): Flow<List<User>> = userDao.getUsers()
        .map { users -> users.map { User(it.id.toString(), it.name, it.username, it.email) } }
}