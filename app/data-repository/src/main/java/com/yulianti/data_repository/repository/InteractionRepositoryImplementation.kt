package com.yulianti.data_repository.repository

import com.example.forex.com.yulianti.mynewmodule.entity.Interaction
import com.example.forex.repository.InteractionRepository
import com.yulianti.data_repository.data_source.local.InteractionLocalDataSource
import com.yulianti.data_repository.data_source.remote.InteractionRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class InteractionRepositoryImplementation(
    private val remoteDataSource: InteractionRemoteDataSource,
    private val localDataSource: InteractionLocalDataSource
) : InteractionRepository {
    override fun getInteraction(): Flow<Interaction> = localDataSource.getInteraction()

    override fun updateInteraction(interaction: Interaction) = flow {
        localDataSource.updateInteraction(interaction)
        this.emit(Unit)
    }.flatMapLatest { getInteraction() }
}