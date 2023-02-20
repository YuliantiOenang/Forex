package com.example.forex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.forex.common.Resource
import com.example.forex.domain.usecase.DeleteDbUseCase
import com.example.forex.domain.usecase.GetMarketListUseCase
import com.example.forex.domain.repository.MarketRepository
import com.example.forex.presentation.market.MarketViewModel
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