package com.yulianti.data_repository.data_source.remote.injection

import com.yulianti.data_repository.data_source.remote.PostRemoteDataSource
import com.yulianti.data_repository.data_source.remote.UserRemoteDataSource
import com.yulianti.data_repository.data_source.remote.source.PostRemoteDataSourceImpl
import com.yulianti.data_repository.data_source.remote.source.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun userRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun postRemoteDataSource(postRemoteDataSourceImpl: PostRemoteDataSourceImpl): PostRemoteDataSource
}