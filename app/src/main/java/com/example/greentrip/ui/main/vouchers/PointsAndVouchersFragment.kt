package com.example.greentrip.ui.main.vouchers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentPointsAndVouchersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PointsAndVouchersFragment : Fragment() {

    lateinit var binding: FragmentPointsAndVouchersBinding
    private val viewModel: PointsAndVouchersViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPointsAndVouchersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStates()
        binding.btnMore.setOnClickListener {
            findNavController().navigate(R.id.action_pointsAndVouchersFragment_to_allVouchersFragment)
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

                        binding.txtPoints.text = it.profile?.data?.data?.points.toString()
                        binding.txtVoucher.text = it.profile?.data?.data?.vouchers?.size.toString()

                        val image = "${Constants.BASEURL}img/pointImg/${ it.profile?.data?.data?.vouchers?.get(0)?.reward?.pointOfInterest?.photo}"
                        Glide.with(binding.root).load(image).into(binding.voucherImg)

                        binding.txtName.text = it.profile?.data?.data?.vouchers?.get(0)?.reward?.title

                    }

                }

        }
    }


}