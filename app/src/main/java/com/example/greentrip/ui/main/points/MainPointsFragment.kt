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
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.adapter.PointsAdapter
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.FragmentMainPointsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainPointsFragment : Fragment() {

lateinit var binding: FragmentMainPointsBinding
private val viewModel: MainPointsViewModel by viewModels()
    val adapter : PointsAdapter by lazy {
        PointsAdapter()
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
        // Inflate the layout for this fragment
        binding = FragmentMainPointsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                        adapter.submitList(it.points?.data)
                        binding.RecyclerPoints.adapter = adapter

                    }

                }

        }
    }


}