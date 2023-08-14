package com.example.greentrip.ui.main.points

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentPointDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PointDetailsFragment : Fragment() {

    lateinit var binding: FragmentPointDetailsBinding
    private val viewModel: MainPointsViewModel by viewModels()
    val id: PointDetailsFragmentArgs by navArgs()

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
        collectStates()

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
                        val image =
                            "${Constants.BASEURL}img/pointImg/${it.specificPoint?.data?.data?.photo}"
                        Glide.with(requireContext()).load(image).into(binding.pointImg)
                        binding.txtPointName.text = it.specificPoint?.data?.data?.name
                        binding.txtDesc.text = it.specificPoint?.data?.data?.address
                        binding.txtPoint.text = "${it.specificPoint?.data?.data?.costPoints} points"
                        binding.txtAddress.text = it.specificPoint?.data?.data?.address
//                        binding.txtRegion.text = it.specificPoint?.data?.data?.
                        binding.txtName.text = it.specificPoint?.data?.data?.agent?.name
                        binding.txtPhone.text = it.specificPoint?.data?.data?.agent?.phone
                        binding.txtEmail.text = it.specificPoint?.data?.data?.agent?.email
                        binding.txtReservation.text =
                            it.specificPoint?.data?.data?.availableTickets.toString()


                    }

                }

        }

    }


}