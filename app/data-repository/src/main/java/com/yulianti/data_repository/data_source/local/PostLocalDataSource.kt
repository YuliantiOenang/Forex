package com.yulianti.data_repository.data_source.local

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalDataSource {
    fun getPosts(): Flow<List<Post>>
    fun getPost(userId: String): Flow<Post>
    suspend fun insertPost(post: List<Post>)
}