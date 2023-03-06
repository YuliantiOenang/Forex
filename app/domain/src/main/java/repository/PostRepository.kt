package com.example.forex.repository

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    fun getPost(userId: String): Flow<Post>
}