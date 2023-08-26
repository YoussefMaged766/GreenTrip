package com.example.greentrip.ui.main.reservation

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
import com.example.greentrip.databinding.FragmentReservationInfoBinding
import com.example.greentrip.models.BookingModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class ReservationInfoFragment : Fragment() {

    lateinit var binding: FragmentReservationInfoBinding
    private val viewModel: ReservationViewModel by viewModels()
    val id: ReservationInfoFragmentArgs by navArgs()
    val status: ReservationInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        collectStates()
        collectCancelBookingStates()

        binding.btnCancel.setOnClickListener {
            lifecycleScope.launch {
                viewModel.cancelBooking( BookingModel(status ="canceled"),id.id)
            }
        }
    }

    private fun callApi() {
        lifecycleScope.launch {
            viewModel.getSpecificBooking(id.id)
        }

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
                    if (!it.isLoading && it.status == "success") {

                        if (it.specificReservation?.data?.data?.point?.photo != null) {
//                            val image =
//                                "${Constants.BASEURL}img/pointImg/${it.specificReservation.data.data.point.photo}"
//                            Glide.with(requireContext()).load(image).into(binding.pointImg)

                            Glide.with(requireContext()).load(it.specificReservation.data.data.point.photo).into(binding.pointImg)
                        } else {
//                            val image =
//                                "${Constants.BASEURL}img/pointImg/${it.specificReservation?.data?.data?.activity?.pointOfInterest?.photo}"
//                            Glide.with(requireContext()).load(image).into(binding.pointImg)

                            Glide.with(requireContext()).load(it.specificReservation?.data?.data?.activity?.pointOfInterest?.photo).into(binding.pointImg)
                        }

                        if (it.specificReservation?.data?.data?.point != null) {
                            binding.txtPointName.text = it.specificReservation.data.data.point.name
                            binding.txtAddress.text = it.specificReservation.data.data.point.address
                            binding.txtRegion.text = it.specificReservation.data.data.point.region
                            binding.txtCategory.text =
                                it.specificReservation.data.data.point.category
                            binding.txtName.text =
                                it.specificReservation.data.data.point.agent?.name
                            binding.txtPhone.text =
                                it.specificReservation.data.data.point.agent?.phone
                            binding.txtEmail.text =
                                it.specificReservation.data.data.point.agent?.email
                            binding.txtNamePoint.text = it.specificReservation.data.data.point.name
                            binding.txtNumDays.text =
                                it.specificReservation.data.data.numOfDays.toString()
                            binding.txtNumPeople.text =
                                it.specificReservation.data.data.numOfTickets.toString()
                            binding.txtStartDate.text =
                                convertDateFormat(it.specificReservation.data.data.createdAt.toString())
                            binding.txtEndDate.text = getEndDate(
                                it.specificReservation.data.data.createdAt.toString(),
                                it.specificReservation.data.data.numOfDays!!
                            )

                        } else {
                            binding.txtPointName.text =
                                it.specificReservation?.data?.data?.activity?.name
                            binding.txtAddress.text =
                                it.specificReservation?.data?.data?.activity?.pointOfInterest?.address
                            binding.txtRegion.text =
                                it.specificReservation?.data?.data?.activity?.pointOfInterest?.region
                            binding.txtCategory.text =
                                it.specificReservation?.data?.data?.activity?.pointOfInterest?.category
                            binding.txtName.text =
                                it.specificReservation?.data?.data?.activity?.pointOfInterest?.agent?.name
                            binding.txtPhone.text =
                                it.specificReservation?.data?.data?.activity?.pointOfInterest?.agent?.phone
                            binding.txtEmail.text =
                                it.specificReservation?.data?.data?.activity?.pointOfInterest?.agent?.email
                            binding.txtNamePoint.text =
                                it.specificReservation?.data?.data?.activity?.name
                            binding.txtNumDays.text =
                                it.specificReservation?.data?.data?.numOfDays.toString()
                            binding.txtNumPeople.text =
                                it.specificReservation?.data?.data?.numOfTickets.toString()
                            binding.txtStartDate.text =
                                convertDateFormat(it.specificReservation?.data?.data?.createdAt.toString())
                            binding.txtEndDate.text = getEndDate(
                                it.specificReservation?.data?.data?.createdAt.toString(),
                                it.specificReservation?.data?.data?.numOfDays!!
                            )

                        }

                        if (status.status == "pending") {
                            binding.txtPending.visibility = View.VISIBLE
                        } else if (status.status == "active") {
                            binding.btnCancel.visibility = View.VISIBLE
                        }else{
                            binding.txtBookCancel.visibility = View.VISIBLE
                        }

                    }

                }

        }
    }

    private fun collectCancelBookingStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateCancelBooking
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {
                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading
                    if (!it.isLoading && it.status == "success") {

                        Toast.makeText(requireContext(), it.status, Toast.LENGTH_SHORT).show()
                        binding.txtBookCancel.visibility = View.VISIBLE
                        binding.btnCancel.visibility = View.GONE

                    }

                }

        }
    }


    private fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }

     private fun getEndDate(startDate :String, days :Int):String{
         val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
         val outputFormat = SimpleDateFormat("M/d/yyyy", Locale.US)

         // Parse the input date string
         val inputDate = inputFormat.parse(startDate)

         // Add two days to the parsed date
         val calendar = Calendar.getInstance()
         calendar.time = inputDate
         calendar.add(Calendar.DAY_OF_MONTH, days)

         // Format the resulting date to the desired output format
         val outputDateStr = outputFormat.format(calendar.time)

         return outputDateStr
     }


}