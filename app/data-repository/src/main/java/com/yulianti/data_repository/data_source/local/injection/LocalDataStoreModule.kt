package com.yulianti.data_repository.data_source.local.injection

import com.yulianti.data_repository.data_source.local.InteractionLocalDataSource
import com.yulianti.data_repository.data_source.local.PostLocalDataSource
import com.yulianti.data_repository.data_source.local.UserLocalDataSource
import com.yulianti.data_repository.data_source.local.source.InteractionLocalDataSourceImpl
import com.yulianti.data_repository.data_source.local.source.PostLocalDataSourceImpl
import com.yulianti.data_repository.data_source.local.source.UserLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module

@InstallIn(SingletonComponent::class)

abstract class LocalDataSourceModule {

    @Binds

    abstract fun bindPostDataSource(lostDataSourceImpl: PostLocalDataSourceImpl): PostLocalDataSource

    @Binds

    abstract fun bindUserDataSource(userDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds

    abstract fun bindInteractionDataStore(interactionDataStore: InteractionLocalDataSourceImpl): InteractionLocalDataSource

}