package com.yulianti.data_repository.repository

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import com.example.forex.repository.PostRepository
import com.yulianti.data_repository.data_source.local.PostLocalDataSource
import com.yulianti.data_repository.data_source.remote.PostRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class PostRepositoryImplementation(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) : PostRepository {
    override fun getPosts(): Flow<List<Post>> = remoteDataSource.getPosts().onEach {
        localDataSource.insertPost(it)
    }


    override fun getPost(postId: String): Flow<Post> = remoteDataSource.getPost(postId).onEach {
        localDataSource.insertPost(listOf(it))
    }
}