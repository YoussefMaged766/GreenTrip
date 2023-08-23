package com.example.greentrip.ui.main.rewards

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentRewardDetailsBinding
import com.example.greentrip.models.BookingModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Locale

@AndroidEntryPoint
class RewardDetailsFragment : Fragment() {

    lateinit var binding: FragmentRewardDetailsBinding
    val id: RewardDetailsFragmentArgs by navArgs()
    val viewModel: RewardsViewModel by viewModels()
    lateinit var dialog: Dialog
    var point: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRewardDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        collectStates()
        redeemPoint()
        collectStateAfterRedeem()
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.rewardsFragment)
        }
    }

    private fun callApi() {
        lifecycleScope.launch {
            viewModel.getSpecificReward(id.id)
        }


    }

    private fun redeemPoint() {
        binding.btnRescue.setOnClickListener {
            lifecycleScope.launch {
                viewModel.createVoucher(BookingModel(reward = point))
            }
        }

    }


    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateReward
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {
                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading
                    if (!it.isLoading && it.status == "success") {
                        val image =
                            "${Constants.BASEURL}img/pointImg/${it.reward?.data?.data?.pointOfInterest?.photo}"
                        Glide.with(requireContext()).load(image).into(binding.pointImg)
                        binding.txtPointName.text = it.reward?.data?.data?.pointOfInterest?.name
                        binding.txtTitle.text = it.reward?.data?.data?.title
                        binding.txtDesc.text = it.reward?.data?.data?.description
                        binding.txtAddress.text = it.reward?.data?.data?.pointOfInterest?.address
//                        binding.txtRegion.text = it.specificPoint?.data?.data?.
                        binding.txtPoints.text =
                            it.reward?.data?.data?.costPoints.toString()

                        point = it.reward?.data?.data?._id

                        binding.txtValid.text =
                            getDays(it.reward?.data?.data?.expireDate.toString())


                    }

                }

        }

    }

    private fun collectStateAfterRedeem() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.statePoint
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.isLoading.toString())
                }
                .collectLatest {
                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading

                    if (!it.isLoading && it.status == "success") {
                        showDialog()
                    }
                    if (!it.isLoading &&it.status != null) {
                        Toast.makeText(requireContext(), it.status, Toast.LENGTH_SHORT).show()
                    }

                }

        }
    }

    private fun getDays(expireDate: String): String {
        val dateString = expireDate
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val instant = date?.toInstant()

        val currentDate = Instant.now()
        val daysDifference = ChronoUnit.DAYS.between(instant, currentDate)
        return "$daysDifference days"
    }

    private fun showDialog() {

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.reedem_dialog)
        dialog.setCancelable(false)
        val btnOk = dialog.findViewById<Button>(R.id.btnOk)
        val txt = dialog.findViewById<TextView>(R.id.txt2)
        val pointsVouchers = getString(R.string.reedem_point)
        val pointsVouchersParts = pointsVouchers.split("Points and Vouchers")

        val boldStyle = StyleSpan(Typeface.BOLD)

        val formattedString = SpannableStringBuilder()
            .append(pointsVouchersParts[0])
            .append(
                "Points and Vouchers",
                boldStyle,
                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            .append(pointsVouchersParts[1])

        txt.text = formattedString

        btnOk.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.homeFragment)
        }
        dialog.show()

    }

}