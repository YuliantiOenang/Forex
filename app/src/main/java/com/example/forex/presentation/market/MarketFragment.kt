package com.example.forex.presentation.market

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forex.common.Resource
import com.example.forex.common.Util.Companion.formatCurrency
import com.example.forex.databinding.FragmentMarketsBinding
import com.example.forex.domain.repository.model.Instrument
import com.example.forex.domain.repository.model.Market
import com.example.forex.presentation.common.DialogLoading
import kotlinx.coroutines.launch

class MarketFragment : Fragment() {
    private var _binding: FragmentMarketsBinding? = null
    private val binding get() = _binding!!

    private lateinit var loading: DialogLoading

    private var firstLaunch = true
    private lateinit var adapter: InstrumentAdapter
    private val viewModel: MarketViewModel by activityViewModels()

    lateinit var equity: State<MarketState>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = DialogLoading(requireContext())
    }

    override fun onStart() {
        super.onStart()
//        handler.postDelayed(refresh, 5000)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading.show()
        val isLoading = Observer<Boolean> { value ->
            if (value && firstLaunch) {
                loading.show()
            } else {
                loading.hide()
            }
        }
        viewModel.isLoadData.observe(viewLifecycleOwner, isLoading)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                while (true) {
                    viewModel.equityState.collect {
                        if (it is MarketState.Success) {
                            binding.txtEquityValue.text = formatCurrency(it.market.data?.account?.equity ?: 0.0f)
                            binding.txtBalanceValue.text = formatCurrency(it.market.data?.account?.balance ?: 0.0f)
                            binding.txtMarginValue.text = formatCurrency(it.market.data?.account?.margin ?: 0.0f)
                            binding.txtUsedValue.text = formatCurrency(it.market.data?.account?.used ?: 0.0f)
                            adapter.setData(it.market.data?.listMarket ?: listOf<Instrument>())
                            loading.hide()
                        }
                    }
//                }
            }
        }

        val error = Observer<String> { instruments ->
            Toast.makeText(requireContext(), instruments, Toast.LENGTH_LONG).show()
        }
        viewModel.error.observe(viewLifecycleOwner, error)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("FIRST_LAUNCH", firstLaunch)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        firstLaunch = savedInstanceState?.getBoolean("FIRST_LAUNCH") ?: firstLaunch

        if (firstLaunch) {
            viewModel.deleteDb()
            firstLaunch = false
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        requireActivity().title = ""
        adapter = InstrumentAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        return root
    }

    override fun onDestroyView() {
        viewModel.cancelJob()
        super.onDestroyView()
        _binding = null
    }
}

sealed interface MarketState {
    /**
     * The feed is still loading.
     */
    object Loading : MarketState

    /**
     * The feed is loaded with the given list of news resources.
     */
    data class Success(
        /**
         * The list of news resources contained in this feed.
         */
        val market: Resource<Market>,
    ) : MarketState
}