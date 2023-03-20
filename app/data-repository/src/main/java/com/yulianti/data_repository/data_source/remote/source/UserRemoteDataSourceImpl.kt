package com.yulianti.data_repository.data_source.remote.source

import com.example.forex.com.yulianti.mynewmodule.entity.User
import com.yulianti.data_repository.data_source.remote.UserRemoteDataSource
import com.yulianti.data_repository.data_source.remote.networking.user.UserApiModel
import com.yulianti.data_repository.data_source.remote.networking.user.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRemoteDataSourceImpl(private val userService: UserService) : UserRemoteDataSource {
    override fun getUserFromRemote(id: String): Flow<User> = flow {
        emit(userService.getUser(id.toLong()))
    }.map { convert(it) }.catch { com.yulianti.mynewmodule.entity.Error.UserException(it) }

    override fun getUsers(): Flow<List<User>> = flow { emit(userService.getUsers()) }.map { users ->
        users.map {
            convert(it)
        }
    }.catch { com.yulianti.mynewmodule.entity.Error.UserException(it) }

    private fun convert(userApiModel: UserApiModel) = User(
        userApiModel.id.toString(),
        userApiModel.name,
        userApiModel.username,
        userApiModel.email
    )
}