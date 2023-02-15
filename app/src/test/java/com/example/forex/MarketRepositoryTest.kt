package com.example.forex

import com.example.forex.data.database.dao.InstrumentDao
import com.example.forex.data.database.dao.InstrumentSymbolDao
import com.example.forex.data.database.entity.InstrumentEntity
import com.example.forex.data.database.entity.InstrumentSymbolEntity
import com.example.forex.data.network.InstrumentDataSource
import com.example.forex.data.network.model.InstrumentListDto
import com.example.forex.data.network.model.InstrumentLiveDto
import com.example.forex.domain.repository.MarketRepositoryImpl
import com.example.forex.domain.repository.model.Account
import com.example.forex.domain.repository.model.Instrument
import com.example.forex.domain.repository.model.InstrumentCode
import com.example.forex.data.sharedpreferences.LocalDataStoreConstant
import com.example.forex.data.sharedpreferences.SharedPreferenceLocalDataStore
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InstrumentEntityRepositoryTest {
    private lateinit var instrumentRepository: MarketRepositoryImpl
    private val networkDataSource: InstrumentDataSource = mockk(relaxed = true)
    private val dao: InstrumentDao = mockk(relaxed = true)
    private val symbolDao: InstrumentSymbolDao = mockk(relaxed = true)
    private val marketLocalDataStore: SharedPreferenceLocalDataStore = mockk(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        instrumentRepository = spyk(
            MarketRepositoryImpl(
                networkDataSource,
                dao,
                symbolDao,
                marketLocalDataStore
            ), recordPrivateCalls = true
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Get instrument list from db if empty, get from cloud`() = runTest {
        every { symbolDao.getAll() } returns mutableListOf()
        coEvery { networkDataSource.getInstrumentList() } returns InstrumentListDto(
            mapOf(
                Pair(
                    "IDR",
                    "Rupiah"
                )
            ), true
        )

        val expectedResult = listOf(
            InstrumentCode("IDR")
        )

        launch {
            instrumentRepository.getInstrumentList()
        }
        advanceUntilIdle()

        verify {
            symbolDao.getAll()
        }
        coVerify {
            networkDataSource.getInstrumentList()
        }
        assertEquals(instrumentRepository.getInstrumentList(), expectedResult)
    }

    @Test
    fun `Get instrument list from db only`() = runTest {
        every { symbolDao.getAll() } returns mutableListOf(InstrumentSymbolEntity("USD"))
        coEvery { networkDataSource.getInstrumentList() } returns InstrumentListDto(
            mapOf(
                Pair(
                    "USD",
                    "United State Dollar"
                )
            ), true
        )

        val expectedResult = listOf(InstrumentCode("USD"))
        launch {
            instrumentRepository.getInstrumentList()
        }
        advanceUntilIdle()
        verify(exactly = 1) {
            symbolDao.getAll()
        }

        assertEquals(instrumentRepository.getInstrumentList(), expectedResult)
    }

    @Test
    fun `Get instrument live when db empty should return list of instrument from cloud`() =
        runTest {
            every { dao.getAll() } returns mutableListOf()
            coEvery { networkDataSource.getInstrumentLive(any(), any()) } returns InstrumentLiveDto(
                "USD",
                mapOf(Pair("USDIDR", 2.0f)),
                12345,
                true
            )

            val expectedResult = listOf(Instrument("USDIDR", 2.0f, 0.0f, 20.0f, 30.0f))
            launch {
                instrumentRepository.getInstrumentLive("", "")
            }
            advanceUntilIdle()
            verify(exactly = 1) {
                dao.getAll()
                dao.insert(any())
            }

            coVerify(exactly = 1) {
                networkDataSource.getInstrumentLive("", "")
            }

            assertEquals(
                instrumentRepository.getInstrumentLive("", "")[0].symbol,
                expectedResult[0].symbol
            )
            assertEquals(
                instrumentRepository.getInstrumentLive("", "")[0].originalPrice,
                expectedResult[0].originalPrice
            )
            assertEquals(
                instrumentRepository.getInstrumentLive("", "")[0].change,
                expectedResult[0].change
            )
        }

    @Test
    fun `Get instrument live when db not empty should return list of instrument from cloud with different price`() =
        runTest {
            every { dao.getAll() } returns mutableListOf(
                InstrumentEntity(
                    "USDIDR",
                    2.0f,
                    10.0f,
                    20.0f
                )
            )
            coEvery { networkDataSource.getInstrumentLive(any(), any()) } returns InstrumentLiveDto(
                "USD",
                mapOf(Pair("USDIDR", 3.0f)),
                12345,
                true
            )

            val expectedResult = listOf(Instrument("USDIDR", 2.0f, 1.0f, 20.0f, 30.0f))
            launch {
                instrumentRepository.getInstrumentLive("", "")
            }
            advanceUntilIdle()
            verify(exactly = 1) {
                dao.getAll()
            }
            coVerify {
                networkDataSource.getInstrumentLive("", "")
            }

            assertEquals(
                instrumentRepository.getInstrumentLive("", "")[0].symbol,
                expectedResult[0].symbol
            )
            assertEquals(
                instrumentRepository.getInstrumentLive("", "")[0].originalPrice,
                expectedResult[0].originalPrice
            )
        }

    @Test
    fun `Get account success return account information`() = runTest {
        every { marketLocalDataStore.readFloat(LocalDataStoreConstant.CURRENT_MONEY) } returns 10000f

        val data = listOf(
            Instrument("USDHKD", 1.0f, 2.0f, 3.0f, 4.0f),
            Instrument("USDJPY", 2.0f, 3.0f, 4.0f, 5.0f),
            Instrument("USDGBP", 3.0f, 4.0f, 5.0f, 6.0f),
            Instrument("USDAUD", 4.0f, 5.0f, 6.0f, 7.0f),
            Instrument("USDCHF", 5.0f, 6.0f, 7.0f, 8.0f)
        )
        launch {
            instrumentRepository.getAccount(data)
        }
        advanceUntilIdle()
        assertEquals(
            instrumentRepository.getAccount(data),
            Account(122833.336f, 50000.0f, 12345.0f, 12345.0f)
        )
    }

    @Test
    fun `Delete DB success`() = runTest {
        every { dao.deleteAll() } returns 10
        every { symbolDao.deleteAll() } returns 10

        launch {
            instrumentRepository.deleteDb()
        }
        advanceUntilIdle()

        verify {
            dao.deleteAll()
            symbolDao.deleteAll()
        }
    }
}