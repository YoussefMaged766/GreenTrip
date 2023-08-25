package com.example.greentrip.ui.main.reservation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.greentrip.R
import com.example.greentrip.adapter.ReservationAdapter
import com.example.greentrip.databinding.FragmentAllReservationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllReservationFragment : Fragment() {

    lateinit var binding: FragmentAllReservationBinding
    private val viewModel: ReservationViewModel by viewModels()
     val pendingAdapter : ReservationAdapter by lazy {
        ReservationAdapter()
    }
    val activeAdapter : ReservationAdapter by lazy {
        ReservationAdapter()
    }
    val cancelAdapter  : ReservationAdapter by lazy {
        ReservationAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStates()

        lifecycleScope.launch {
            viewModel.getAllBooking()
        }
    }


    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {

                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading

                    if (!it.isLoading && it.status == "success") {

                        it.reservation?.data?.let { reservationList ->

                            val pendingList = reservationList.filter { it.status == "pending" }
                            if (pendingList.isNotEmpty()) {
                                pendingAdapter.submitList(pendingList)
                                binding.recyclerPending.adapter = pendingAdapter
                                binding.recyclerPending.visibility = View.VISIBLE
                                binding.txtPending.visibility = View.VISIBLE
                                Log.e("collectStatesReservation: ", pendingList.size.toString())
                            } else {
                                binding.txtPending.visibility = View.GONE
                                binding.recyclerPending.visibility = View.GONE
                            }

                            val activeList = reservationList.filter { it.status == "active" }
                            if (activeList.isNotEmpty()) {
                                activeAdapter.submitList(activeList)
                                binding.recyclerActive.adapter = activeAdapter
                                binding.recyclerActive.visibility = View.VISIBLE
                            } else {
                                binding.txtActive.visibility = View.GONE
                                binding.recyclerActive.visibility = View.GONE
                            }

                            val cancelList = reservationList.filter { it.status == "canceled" }
                            if (cancelList.isNotEmpty()) {
                                cancelAdapter.submitList(cancelList)
                                binding.recyclerCanceled.adapter = cancelAdapter
                                binding.recyclerCanceled.visibility = View.VISIBLE
                            } else {
                                binding.txtCanceled.visibility = View.GONE
                                binding.recyclerCanceled.visibility = View.GONE
                            }
                        }

                    }

                }

        }
    }
}