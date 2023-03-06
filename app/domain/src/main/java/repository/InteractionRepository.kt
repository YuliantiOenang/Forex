package com.example.forex.repository

import com.example.forex.com.yulianti.mynewmodule.entity.Interaction
import kotlinx.coroutines.flow.Flow

interface InteractionRepository {
    fun getInteraction(): Flow<Interaction>
    fun updateInteraction(interaction: Interaction): Flow<Interaction>
}