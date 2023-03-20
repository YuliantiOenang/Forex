package com.example.forex.usecase

import com.example.forex.com.yulianti.mynewmodule.entity.User
import repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class GetUser(val dispatcher: CoroutineDispatcher, val userRepository: UserRepository) :
    usecase<GetUser.Request, GetUser.Result>(Configuration(dispatcher)) {
    data class Request(val id: String): Input
    data class Result(val user: User): Output

    override fun process(request: Request) = userRepository.getUser(request.id).map {
        Result(it)
    }
}