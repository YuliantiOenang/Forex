package com.example.forex.core

import android.util.Log
import com.example.forex.core.datastore.ChangeListVersions
import com.example.forex.core.network.model.NetworkChangeList
import java.util.concurrent.CancellationException

interface Synchronizer {
    suspend fun getChangeListVersions(): ChangeListVersions
    suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions)
    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}
interface Syncable {
    public suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

//Function ini untuk ngebungkus resultnya dalam Result.success/ Result.failure
private suspend fun<T> suspendRunCaching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i(
        "suspendRunCatching",
        "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result",
        exception,
    )
    Result.failure(exception)
}

suspend fun Synchronizer.changeListSync(
    versionReader: (ChangeListVersions) -> Int,
    changeListFetcher: suspend (Int) -> List<NetworkChangeList>,
    versionUpdater: ChangeListVersions.(Int) -> ChangeListVersions,
    modelDeleter: suspend (List<String>) -> Unit,
    modelUpdater: suspend (List<String>) -> Unit
) = suspendRunCaching {
    val currentVersion = versionReader(getChangeListVersions())
    val changeList = changeListFetcher(currentVersion)
    if (changeList.isEmpty()) return@suspendRunCaching true

    val (deleted, updated) = changeList.partition(NetworkChangeList::isDelete)

    modelDeleter(deleted.map(NetworkChangeList::id))
    modelUpdater(updated.map(NetworkChangeList::id))

    val lastVersion = changeList.last().changeListVersion
    updateChangeListVersions {
        versionUpdater(lastVersion)
    }
}.isSuccess