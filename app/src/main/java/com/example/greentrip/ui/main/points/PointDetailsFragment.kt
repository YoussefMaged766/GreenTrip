package com.example.greentrip.ui.main.points

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentPointDetailsBinding
import com.example.greentrip.models.BookingModel
import com.example.greentrip.ui.activity.HomeActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PointDetailsFragment : Fragment() {

    lateinit var binding: FragmentPointDetailsBinding
    private val viewModel: MainPointsViewModel by viewModels()
    private  val sharedViewModel  by activityViewModels<HomeActivityViewModel>()
    val id: PointDetailsFragmentArgs by navArgs()
    var point: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPointDetailsBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getSpecificPoints(id.id)

        }
        binding.txtMore.setOnClickListener {
            val action =
                PointDetailsFragmentDirections.actionPointDetailsFragmentToAllActivityFragment(id.id)
            findNavController().navigate(action)
        }

        binding.btnBook.setOnClickListener {
            val action =
                PointDetailsFragmentDirections.actionPointDetailsFragmentToPointsConfirmBookingFragment(
                    id.id
                )
            findNavController().navigate(action)
        }

        collectStates()
        collectStatesPoints()
        binding.img.setOnClickListener {
            addPoint()
        }

    }

    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.statePoint
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {
                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading
                    if (!it.isLoading && it.status == "success") {
//                        val image =
//                            "${Constants.BASEURL}img/pointImg/${it.specificPoint?.data?.data?.photo}"
//                        Glide.with(requireContext()).load(image).into(binding.pointImg)
                        Glide.with(requireContext()).load(it.specificPoint?.data?.data?.photo).into(binding.pointImg)

                        binding.txtPointName.text = it.specificPoint?.data?.data?.name
                        binding.txtDesc.text = it.specificPoint?.data?.data?.description
                        binding.txtPoint.text = "${it.specificPoint?.data?.data?.costPoints} points"
                        binding.txtAddress.text = it.specificPoint?.data?.data?.address
//                        binding.txtRegion.text = it.specificPoint?.data?.data?.
                        binding.txtName.text = it.specificPoint?.data?.data?.agent?.name
                        binding.txtPhone.text = it.specificPoint?.data?.data?.agent?.phone
                        binding.txtEmail.text = it.specificPoint?.data?.data?.agent?.email
                        binding.txtReservation.text =
                            it.specificPoint?.data?.data?.availableTickets.toString()

                        point = it.specificPoint?.data?.data?.costPoints?.toInt()


                    }

                }

        }

    }


    private fun addPoint() {
        lifecycleScope.launch {

            viewModel.addPoint(BookingModel(points = point))

        }
    }

    private fun collectStatesPoints() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateAddPoint
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {
                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading
                    if (!it.isLoading && it.status == "success") {

                        Toast.makeText(requireContext(), it.status, Toast.LENGTH_SHORT).show()
                        sharedViewModel.updatePoints.value = true
                        sharedViewModel.getProfile()

                    }

                }

        }
    }


}