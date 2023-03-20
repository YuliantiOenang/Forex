package com.yulianti.data_repository.data_source.local

import com.example.forex.com.yulianti.mynewmodule.entity.Interaction
import kotlinx.coroutines.flow.Flow

interface InteractionLocalDataSource {
    fun getInteraction(): Flow<Interaction>
    suspend fun updateInteraction(interaction: Interaction)
}