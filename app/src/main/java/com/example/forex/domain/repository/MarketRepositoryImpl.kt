package com.example.forex.domain.repository

import com.example.forex.BuildConfig
import com.example.forex.common.Util.Companion.roundOffDecimal
import com.example.forex.core.Synchronizer
import com.example.forex.core.changeListSync
import com.example.forex.core.datastore.ChangeListVersions
import com.example.forex.data.database.dao.InstrumentDao
import com.example.forex.data.database.dao.InstrumentSymbolDao
import com.example.forex.data.database.entity.InstrumentEntity
import com.example.forex.data.database.entity.InstrumentSymbolEntity
import com.example.forex.data.network.InstrumentDataSource
import com.example.forex.domain.repository.model.Account
import com.example.forex.domain.repository.model.Instrument
import com.example.forex.domain.repository.model.InstrumentCode
import com.example.forex.data.sharedpreferences.LocalDataStoreConstant.CURRENT_MONEY
import com.example.forex.data.sharedpreferences.SharedPreferenceLocalDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val networkDataSource: InstrumentDataSource,
    private val dao: InstrumentDao,
    private val instrumentSymbolDao: InstrumentSymbolDao,
    private val marketLocalDataStore: SharedPreferenceLocalDataStore
) : MarketRepository {
    private val ONE_LOT_QUANTITY: Int = 100000
    override suspend fun getInstrumentLive(from: String, to: String): List<Instrument> {
        return withContext(Dispatchers.IO) {
            val db = dao.getAll()
            val result = networkDataSource.getInstrumentLive(from, to)
            if (result.quotes != null) {
                result.quotes.map { entry ->
                    val random1: Float = Random().nextInt(10) - 10 / 10.0f
                    val random2: Float = Random().nextInt(10) - 10 / 10.0f
                    val random3: Float = Random().nextInt(10) - 10 / 10.0f
                    val sell = roundOffDecimal(entry.value + random1)
                    val buy = roundOffDecimal(entry.value + random2)

                    val initialPrice: Float
                    val currentPrice: Float

                    val dbItem = db.find { entry.key == it.symbol }
                    // first launch the app
                    if (dbItem == null) {
                        initialPrice = roundOffDecimal(entry.value)
                        currentPrice = initialPrice
                    } else {
                        initialPrice = roundOffDecimal(dbItem.original_price)
                        currentPrice = if (BuildConfig.DEBUG) {
                            roundOffDecimal(entry.value + random3)
                        } else {
                            entry.value
                        }
                    }

                    Instrument(
                        entry.key,
                        initialPrice,
                        roundOffDecimal(currentPrice - initialPrice),
                        sell,
                        buy
                    )
                }
            } else {
                emptyList()
            }
        }
    }

    override fun getAccount(instruments: List<Instrument>): Account {
        var equity = 0.0f
        marketLocalDataStore.writeFloat(CURRENT_MONEY, 10000.0f)
        val currentMoney = marketLocalDataStore.readFloat(CURRENT_MONEY)
        instruments.iterator().forEach { item: Instrument ->
            val temp: Float = currentMoney / (item.originalPrice * ONE_LOT_QUANTITY)
            equity += (item.originalPrice + item.change) * temp * ONE_LOT_QUANTITY
        }
        return Account(equity, currentMoney * instruments.size, 12345.0f, 12345.0f)
    }

    override suspend fun deleteDb() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
            instrumentSymbolDao.deleteAll()
        }
    }

    override suspend fun syncWith(synchronizer: Synchronizer) =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::marketVersion,
            changeListFetcher = {currentVersion ->
                networkDataSource.getMarketChangeList(currentVersion)
            },
            versionUpdater = {latestVersion ->
                copy(latestVersion)
            },
            modelDeleter = {
                dao.deleteAll()
                instrumentSymbolDao.deleteAll()
            },
            modelUpdater = {  ->
                val networkMarketListResources = networkDataSource.getInstrumentList()
                val networkMarketResources = networkDataSource.getInstrumentLive("USD", "AUD")
                if (networkMarketResources.quotes != null) {
                    networkMarketResources.quotes.map { entry ->
                        val db = dao.getAll()
                        val random1: Float = Random().nextInt(10) - 10 / 10.0f
                        val random2: Float = Random().nextInt(10) - 10 / 10.0f
                        val random3: Float = Random().nextInt(10) - 10 / 10.0f
                        val sell = roundOffDecimal(entry.value + random1)
                        val buy = roundOffDecimal(entry.value + random2)

                        val initialPrice: Float
                        val currentPrice: Float

                        val dbItem = db.find { entry.key == it.symbol }
                        // first launch the app
                        if (dbItem == null) {
                            initialPrice = roundOffDecimal(entry.value)
                            currentPrice = initialPrice
                                dao.insert(
                                    InstrumentEntity(
                                        entry.key,
                                        initialPrice,
                                        sell,
                                        buy
                                    )
                                )

                        } else {
                            initialPrice = roundOffDecimal(dbItem.original_price)
                            currentPrice = if (BuildConfig.DEBUG) {
                                roundOffDecimal(entry.value + random3)
                            } else {
                                entry.value
                            }
                        }

                        Instrument(
                            entry.key,
                            initialPrice,
                            roundOffDecimal(currentPrice - initialPrice),
                            sell,
                            buy
                        )
                    }
                    instrumentSymbolDao.insertAll(networkMarketListResources.currencies?.map {
                        return@map InstrumentSymbolEntity(it.key)
                    } ?: listOf())

                }
            }
        )

    override suspend fun getInstrumentList(): List<InstrumentCode> {
        return withContext(Dispatchers.IO) {
            val db = instrumentSymbolDao.getAll()
            if (db.isNotEmpty()) {
                db.map {
                    InstrumentCode(it.symbol)
                }
            } else {
                val resultFromNetwork = networkDataSource.getInstrumentList()
                if (resultFromNetwork.currencies != null) {
                    resultFromNetwork.currencies.map {
                        return@map InstrumentSymbolEntity(it.key)
                    }.let { instrumentSymbolDao.insertAll(it) }
                    resultFromNetwork.currencies.map {
                        return@map InstrumentCode(it.key)
                    }
                } else {
                    emptyList()
                }
            }
        }
    }


}