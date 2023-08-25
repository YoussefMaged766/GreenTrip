package com.example.greentrip.ui.main.points

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentPointsConfirmBookingBinding
import com.example.greentrip.models.BookingModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PointsConfirmBookingFragment : Fragment() {

    lateinit var binding: FragmentPointsConfirmBookingBinding
    val viewModel: MainPointsViewModel by viewModels()
    val data: PointsConfirmBookingFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPointsConfirmBookingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStates()
        collectStatesPoint()

        viewModel.getSpecificPoints(data.idPoint)

        binding.btnRequestReservation.setOnClickListener {
            callApi()
        }

    }

    private fun callApi() {
        viewModel.booking(
            BookingModel(
                type = "point",
                point = data.idPoint,
                numOfDays = binding.txtDaysNum.text.toString().toInt(),
                numOfTickets = binding.txtPeopleNum.text.toString().toInt(),
            )
        )

    }

    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.stateBooking
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {

                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading

                    if (!it.isLoading &&it.status != null) {
                        Toast.makeText(requireContext(), it.status, Toast.LENGTH_SHORT).show()
                    }


                }

        }
    }

    private fun collectStatesPoint() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.statePoint
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {

                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading

                    if (!it.isLoading &&it.status != null) {

                        binding.txtPointName.text = it.specificPoint?.data?.data?.name

                        val image = "${Constants.BASEURL}img/pointImg/${it.specificPoint?.data?.data?.photo}"
                        Glide.with(binding.root).load(image).into(binding.pointImg)



                    }




                }

        }
    }


}