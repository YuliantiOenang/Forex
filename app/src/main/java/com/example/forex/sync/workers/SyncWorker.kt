package com.example.forex.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.forex.core.Dispatcher
import com.example.forex.core.Syncable
import com.example.forex.core.Synchronizer
import com.example.forex.core.datastore.ChangeListVersions
import com.example.forex.domain.repository.MarketRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val niaPreferences: NiaPreferencesDataSource,
    private val marketRepository: MarketRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams), Synchronizer {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }

    override val coroutineContext: CoroutineDispatcher
        get() = super.coroutineContext

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    override suspend fun getChangeListVersions(): ChangeListVersions {
        TODO("Not yet implemented")
    }

    override suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions) {
        TODO("Not yet implemented")
    }

    override suspend fun Syncable.sync(): Boolean {
        TODO("Not yet implemented")
    }
}