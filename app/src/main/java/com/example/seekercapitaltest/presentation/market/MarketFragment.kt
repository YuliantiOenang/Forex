package com.example.seekercapitaltest.presentation.market

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seekercapitaltest.common.Util.Companion.formatCurrency
import com.example.seekercapitaltest.databinding.FragmentMarketsBinding
import com.example.seekercapitaltest.domain.repository.model.Instrument
import com.example.seekercapitaltest.presentation.common.DialogLoading

class MarketFragment : Fragment() {
    private var _binding: FragmentMarketsBinding? = null
    private val binding get() = _binding!!

    private lateinit var loading: DialogLoading

    private var firstLaunch = true
    private lateinit var adapter: InstrumentAdapter
    private val viewModel: MarketViewModel by activityViewModels()
    private val handler = Handler()
    private val refresh: Runnable = object : Runnable {
        override fun run() {
            viewModel.initializeData()
            handler.postDelayed(this, 5000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = DialogLoading(requireContext())
    }

    override fun onStart() {
        super.onStart()
        handler.postDelayed(refresh, 5000)
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

        val error = Observer<String> { instruments ->
            Toast.makeText(requireContext(), instruments, Toast.LENGTH_LONG).show()
        }
        viewModel.error.observe(viewLifecycleOwner, error)

        val instrumentData = Observer<List<Instrument>> { instruments ->
            adapter.setData(instruments)
        }
        viewModel.instrumentLiveData.observe(viewLifecycleOwner, instrumentData)

        val equityData = Observer<Float> { instruments ->
            binding.txtEquityValue.text = formatCurrency(instruments)
        }
        viewModel.equityLiveData.observe(viewLifecycleOwner, equityData)
        val balanceData = Observer<Float> { instruments ->
            binding.txtBalanceValue.text = formatCurrency(instruments)
        }
        viewModel.balanceLiveData.observe(viewLifecycleOwner, balanceData)
        val marginData = Observer<Float> { instruments ->
            binding.txtMarginValue.text = formatCurrency(instruments)
        }
        viewModel.marginLiveData.observe(viewLifecycleOwner, marginData)
        val usedData = Observer<Float> { instruments ->
            binding.txtUsedValue.text = formatCurrency(instruments)
        }
        viewModel.usedLiveData.observe(viewLifecycleOwner, usedData)
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

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(refresh)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(refresh)
        viewModel.cancelJob()
        super.onDestroyView()
        _binding = null
    }
}