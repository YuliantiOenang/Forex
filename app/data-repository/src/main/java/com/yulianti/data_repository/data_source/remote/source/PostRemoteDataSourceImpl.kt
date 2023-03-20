package com.yulianti.data_repository.data_source.remote.source

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import com.yulianti.data_repository.data_source.remote.PostRemoteDataSource
import com.yulianti.data_repository.data_source.remote.networking.post.PostService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PostRemoteDataSourceImpl(private val postService: PostService) : PostRemoteDataSource {
    override fun getPosts(): Flow<List<Post>> = flow {
        emit(postService.getPosts())
    }.map { posts ->
        posts.map {
            Post(it.id, it.userId, it.title, it.body)
        }
    }.catch { com.yulianti.mynewmodule.entity.Error.PostException(it) }

    override fun getPost(postId: String): Flow<Post> = flow {
        emit(postService.getPost(postId.toLong()))
    }.map {
        Post(it.id, it.userId, it.title, it.body)
    }.catch { com.yulianti.mynewmodule.entity.Error.PostException(it) }
}