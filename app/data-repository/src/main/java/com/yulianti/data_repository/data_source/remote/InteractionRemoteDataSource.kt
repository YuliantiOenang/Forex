package com.yulianti.data_repository.data_source.remote

import com.example.forex.com.yulianti.mynewmodule.entity.Interaction
import kotlinx.coroutines.flow.Flow

interface InteractionRemoteDataSource {
    fun getInteraction(): Flow<Interaction>
    fun updateInteraction(interaction: Interaction): Flow<Interaction>
}