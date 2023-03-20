package com.yulianti.data_repository.data_source.local.source

import com.example.forex.com.yulianti.mynewmodule.entity.Post
import com.yulianti.data_repository.data_source.local.PostLocalDataSource
import com.yulianti.data_repository.data_source.local.db.post.PostDao
import com.yulianti.data_repository.data_source.local.db.post.PostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostLocalDataSourceImpl(private val postDao: PostDao) : PostLocalDataSource {
    override fun getPosts(): Flow<List<Post>> =
        postDao.getPosts().map { posts -> posts.map { Post(it.id, it.userId, it.title, it.body) } }

    override suspend fun insertPost(post: List<Post>) {
        postDao.insertPosts(post.map { PostEntity(it.id, it.userId, it.title, it.body) })
    }
}