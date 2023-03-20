package com.yulianti.data_repository.data_source.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.forex.com.yulianti.mynewmodule.entity.Interaction
import com.yulianti.data_repository.data_source.local.InteractionLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal val KEY_TOTAL_TAPS = intPreferencesKey("key_total_taps")

class InteractionLocalDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    InteractionLocalDataSource {
    override fun getInteraction(): Flow<Interaction> {
        return dataStore.data.map {
            Interaction(it[KEY_TOTAL_TAPS] ?: 0)

        }
    }

    override suspend fun updateInteraction(interaction: Interaction) {
        dataStore.edit {
            it[KEY_TOTAL_TAPS] = interaction.totalClick
        }
    }
}