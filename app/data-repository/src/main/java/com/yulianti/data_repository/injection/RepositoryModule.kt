package com.yulianti.data_repository.injection

import com.example.forex.repository.InteractionRepository
import com.example.forex.repository.PostRepository
import com.yulianti.data_repository.repository.InteractionRepositoryImplementation
import com.yulianti.data_repository.repository.PostRepositoryImplementation
import com.yulianti.data_repository.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostRepository(postRepositoryImplementation: PostRepositoryImplementation): PostRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindInteractionRepository(interactionRepositoryImplementation: InteractionRepositoryImplementation): InteractionRepository
}