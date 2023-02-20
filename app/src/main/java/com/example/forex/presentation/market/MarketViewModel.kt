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
        .map<Resource<Market>, MarketState>{
            if (it is Resource.Loading) {
                MarketState.Loading
            } else {
                MarketState.Success(it)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MarketState.Loading,
        )
    var isLoadData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var error: MutableLiveData<String> = MutableLiveData<String>()
    private var job: Job? = null
    private var deleteJob: Job? = null

    private val _state = mutableStateOf(MarketListState())
    val state: State<MarketListState> = _state

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