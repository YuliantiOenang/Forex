package com.yulianti.data_repository.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yulianti.data_repository.data_source.local.db.post.PostDao
import com.yulianti.data_repository.data_source.local.db.post.PostEntity
import com.yulianti.data_repository.data_source.local.db.user.UserDao
import com.yulianti.data_repository.data_source.local.db.user.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

}