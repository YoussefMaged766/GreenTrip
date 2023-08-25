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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentVoucherQRBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VoucherQRFragment : Fragment() {

    lateinit var binding: FragmentVoucherQRBinding

    private val viewModel: PointsAndVouchersViewModel by viewModels()
    val id :VoucherQRFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentVoucherQRBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStates()

        lifecycleScope.launch {
            viewModel.getSpecificVoucher(id.id)
        }
        startTime()

    }

    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateVoucher
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {
                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading
                    if (!it.isLoading && it.status == "success") {

                        val image = "${Constants.BASEURL}img/qr/${it.specificVoucher?.data?.data?.reward?.pointOfInterest?.qrcode}"
                        Glide.with(binding.root).load(image).into(binding.imgQR)

                        binding.txtTitle.text = it.specificVoucher?.data?.data?.reward?.title
                        binding.txtAddress.text = it.specificVoucher?.data?.data?.reward?.pointOfInterest?.address
                        binding.txtRegion.text = it.specificVoucher?.data?.data?.reward?.pointOfInterest?.region




                    }

                }

        }
    }

    private fun startTime(){
        lifecycleScope.launch {
            viewModel.startTimerWithId(id.id ,requireContext())
        }
    }


}