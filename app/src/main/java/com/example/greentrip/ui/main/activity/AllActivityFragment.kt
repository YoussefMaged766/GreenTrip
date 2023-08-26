package com.example.greentrip.ui.main.activity

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
import com.example.greentrip.adapter.ActivityAdapter
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentAllActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllActivityFragment : Fragment() {

    lateinit var binding: FragmentAllActivityBinding
    private val viewModel: AllActivityViewModel by viewModels()
    val id  :AllActivityFragmentArgs by navArgs()
    private val adapter : ActivityAdapter by lazy {
        ActivityAdapter()
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
        binding = FragmentAllActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getActivity(id.id)
        }
        collectStates()

    }

    private fun collectStates(){
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.state
                .distinctUntilChanged()
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collectLatest {
                    binding.loading.loadingIndicator.isIndeterminate = it.isLoading
                    binding.loading.loadingOverlay.isVisible = it.isLoading

                    if (!it.isLoading && it.status == "success"){
//                        val image =
//                            "${Constants.BASEURL}img/pointImg/${it.activity?.data?.get(0)?.pointOfInterest?.photo}"
//                        Glide.with(requireContext()).load(image).into(binding.pointImg)
                        if (it.activity?.data!!.isNotEmpty()){
                            Glide.with(requireContext()).load(it.activity?.data?.get(0)?.pointOfInterest?.photo).into(binding.pointImg)
                            binding.txtPointName.text = it.activity?.data?.get(0)?.pointOfInterest?.name
                            adapter.submitList(it.activity?.data)
                            binding.RecyclerActivity.adapter = adapter
                        }



                    }

                }

        }
    }


}