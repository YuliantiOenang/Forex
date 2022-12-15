package com.example.seekercapitaltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.seekercapitaltest.common.Resource
import com.example.seekercapitaltest.domain.usecase.DeleteDbUseCase
import com.example.seekercapitaltest.domain.usecase.GetMarketListUseCase
import com.example.seekercapitaltest.domain.repository.MarketRepository
import com.example.seekercapitaltest.domain.repository.model.Account
import com.example.seekercapitaltest.domain.repository.model.Instrument
import com.example.seekercapitaltest.domain.repository.model.InstrumentCode
import com.example.seekercapitaltest.domain.repository.model.Market
import com.example.seekercapitaltest.presentation.market.MarketViewModel
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MarketViewModelTest {
    private lateinit var viewModel: MarketViewModel
    private val marketRepository: MarketRepository = mockk(relaxed = true)
    private val getMarketListUseCase: GetMarketListUseCase = mockk(relaxed = true)
    private val deleteDbUseCase: DeleteDbUseCase = mockk(relaxed = true)

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            MarketViewModel(
                getMarketListUseCase,
                deleteDbUseCase
            ), recordPrivateCalls = true
        )
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `Initialize market list should return market price list`() = runTest {
        val live = mutableListOf(Instrument("USD", 1.0f, 2.0f, 3.0f, 4.0f))
        coEvery { marketRepository.getInstrumentList() } returns mutableListOf(InstrumentCode("USD"))
        coEvery { marketRepository.getInstrumentLive(any(), any()) } returns live
        val account = Account(1.0f, 2.0f, 3.0f, 4.0f)
        coEvery { marketRepository.getAccount(any()) } returns account

        every {
            getMarketListUseCase.invoke(
                any(),
                any()
            )
        } returns flow {
            emit(Resource.Loading())
            val toCode = marketRepository.getInstrumentList()
            val stringTo = toCode.joinToString(", ")
            val instrumentList = marketRepository.getInstrumentLive("USD", stringTo)
            val account = marketRepository.getAccount(instrumentList)
            emit(Resource.Success(Market(account, instrumentList)))
        }

        val job = launch {
            viewModel.initializeData()
        }
        advanceTimeBy(10000)

        verify {
            getMarketListUseCase.invoke(any(), any())
        }
        assertEquals(viewModel.state.value.isLoading, false)
        assertEquals(viewModel.state.value.market.account, account)
        assertEquals(viewModel.state.value.market.listMarket, live)
        job.cancel()
    }

    @Test
    fun `Initialise data error will return error message`() = runTest {
        every {
            getMarketListUseCase.invoke(
                any(),
                any()
            )
        } returns flow {
            emit(Resource.Error("error"))
        }

        val job = launch {
            viewModel.initializeData()
        }
        advanceTimeBy(10000)

        verify {
            getMarketListUseCase.invoke(any(), any())
        }
        assertEquals(viewModel.state.value.error, "error")

        job.cancel()
    }

    @Test
    fun `Delete DB success will success`() = runTest {
        every {
            deleteDbUseCase.invoke()
        } returns flow {
            emit(Resource.Success(true))
        }

        val job = launch {
            viewModel.deleteDb()
        }
        advanceTimeBy(10000)

        verify {
            deleteDbUseCase.invoke()
        }
        job.cancel()
    }

    @Test
    fun `Delete DB success error should throw error message`() = runTest {
        val message = "delete db error"
        every {
            deleteDbUseCase.invoke()
        } returns flow {
            emit(Resource.Error(message))
        }

        val job = launch {
            viewModel.deleteDb()
        }
        advanceTimeBy(10000)

        verify {
            deleteDbUseCase.invoke()
        }
        assertEquals(viewModel.state.value.error, message)
        job.cancel()
    }
}