package com.example.forex.presentation.market

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forex.common.Resource
import com.example.forex.domain.usecase.DeleteDbUseCase
import com.example.forex.domain.usecase.GetMarketListUseCase
import com.example.forex.domain.repository.model.Instrument
import com.example.forex.domain.repository.model.Market
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getMarketListUseCase: GetMarketListUseCase,
    private val deleteDbUseCase: DeleteDbUseCase
) : ViewModel() {
    val equityState: StateFlow<MarketState> = getMarketListUseCase("IDR", "")
        .filterNot { it.data?.listMarket?.isEmpty() ?: false }
        .map(MarketState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MarketState.Loading,
        )
    var balanceLiveData: MutableLiveData<Float> = MutableLiveData<Float>()
    var marginLiveData: MutableLiveData<Float> = MutableLiveData<Float>()
    var usedLiveData: MutableLiveData<Float> = MutableLiveData<Float>()
    var instrumentLiveData: MutableLiveData<List<Instrument>> = MutableLiveData<List<Instrument>>()
    var isLoadData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var error: MutableLiveData<String> = MutableLiveData<String>()
    private var job: Job? = null
    private var deleteJob: Job? = null

    private val _state = mutableStateOf(MarketListState())
    val state: State<MarketListState> = _state

    fun initializeData() {
        if (deleteJob?.isActive != true) {

            job = getMarketListUseCase("IDR","").onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = MarketListState(isLoading = false)
                        isLoadData.postValue(_state.value.isLoading)
                        _state.value = MarketListState(
                            market = result.data!!
                        )
                        instrumentLiveData.postValue(_state.value.market.listMarket)
                        balanceLiveData.postValue(_state.value.market.account.balance)
                        marginLiveData.postValue(_state.value.market.account.margin)
                        usedLiveData.postValue(_state.value.market.account.used)
                    }
                    is Resource.Error -> {
                        _state.value = MarketListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                        error.postValue(_state.value.error)
                    }
                    is Resource.Loading -> {
                        _state.value = MarketListState(isLoading = true)
                        isLoadData.postValue(_state.value.isLoading)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun deleteDb() {
        deleteJob = deleteDbUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = MarketListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    error.postValue(_state.value.error)
                }
                else -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun cancelJob() {
        job?.cancel()
        deleteJob?.cancel()
    }
}