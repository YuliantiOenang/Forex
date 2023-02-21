package com.example.forex.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.*
import com.example.forex.core.Dispatcher
import com.example.forex.core.NiaDispatchers
import com.example.forex.core.Synchronizer
import com.example.forex.core.datastore.ChangeListVersions
import com.example.forex.data.sharedpreferences.SharedPreferenceLocalDataStore
import com.example.forex.domain.repository.MarketRepository
import com.example.forex.sync.initializers.SyncConstraints
import com.example.forex.sync.initializers.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val preference: SharedPreferenceLocalDataStore,
    private val marketRepository: MarketRepository,
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams), Synchronizer {
    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        traceAsync("Sync", 0) {

            // First sync the repositories in parallel
            val syncedSuccessfully = awaitAll(
                async { marketRepository.sync() }
            ).all { it }

            if (syncedSuccessfully) {
                Result.success()
            } else {
                Result.retry()
            }
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return appContext.syncForegroundInfo()
    }

    override suspend fun getChangeListVersions(): ChangeListVersions =
        preference.getChangeListVersions()

    override suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions) =
        preference.updateChangeListVersion(update)

    companion object {
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}
