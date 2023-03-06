package com.example.forex.usecase

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import com.example.forex.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class GetPost(
    val dispatcher: CoroutineDispatcher,
    private val postRepository: PostRepository
): usecase<GetPost.Request, GetPost.Result>(Configuration(dispatcher)) {
    override fun process(request: Request) = postRepository.getPost(request.id).map {
        Result(it)
    }

    data class Request(val id: String): Input
    data class Result(val post: Post): Output
}