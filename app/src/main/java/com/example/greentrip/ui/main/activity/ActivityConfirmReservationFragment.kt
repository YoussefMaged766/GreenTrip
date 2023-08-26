package com.example.greentrip.ui.main.activity

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
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentActivityConfirmReservationBinding
import com.example.greentrip.models.BookingModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ActivityConfirmReservationFragment : Fragment() {
    lateinit var binding:FragmentActivityConfirmReservationBinding
    private val viewModel :AllActivityViewModel by viewModels()
    val data :ActivityConfirmReservationFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentActivityConfirmReservationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStates()
        collectStatesActivity()
        viewModel.getSpecificActivity(data.id)

        binding.btnRequestReservation.setOnClickListener {
            callApi()
        }

    }
    private fun callApi() {
        viewModel.booking(
            BookingModel(
                type = "activity",
                activity = data.id,
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


    private fun collectStatesActivity() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.stateActivity
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {

                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading

                    if (!it.isLoading &&it.status != null) {
                        binding.txtActivityName.text = it.specificPoint?.data?.data?.name
                        binding.txtPointName.text = it.specificPoint?.data?.data?.pointOfInterest?.name

//                        val image = "${Constants.BASEURL}img/pointImg/${it.specificPoint?.data?.data?.pointOfInterest?.photo}"
//                        Glide.with(binding.root).load(image).into(binding.pointImg)

                        Glide.with(binding.root).load(it.specificPoint?.data?.data?.pointOfInterest?.photo).into(binding.pointImg)

                    }




                }

        }
    }



}