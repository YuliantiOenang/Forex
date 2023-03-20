package com.yulianti.data_repository.data_source.local.source

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import com.yulianti.data_repository.data_source.local.PostLocalDataSource
import kotlinx.coroutines.flow.Flow

class PostUserLocalDataSourceImpl: PostLocalDataSource {
    override fun getPosts(): Flow<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun getPost(userId: String): Flow<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun insertPost(post: List<Post>) {
        TODO("Not yet implemented")
    }
}