package com.yulianti.data_repository.data_source.remote

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import kotlinx.coroutines.flow.Flow

interface PostRemoteDataSource {
    fun getPosts(): Flow<List<Post>>
    fun getPost(userId: String): Flow<Post>
}