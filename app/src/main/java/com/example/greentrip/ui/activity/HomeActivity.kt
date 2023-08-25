package com.example.greentrip.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.ActivityHomeBinding
import com.example.greentrip.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        collectStates()
        updatePoints()


    }

    private fun collectStates() {
        lifecycleScope.launch {
            viewModel.state
                .onEach {
                    Log.e("collectStates: ", it.status.toString())
                }
                .collect {

                    if (it.status == "success") {
                        binding.btnPoints.text = "${it.profile?.data?.data?.points.toString()}Pts"

                    }


                }

        }


    }

    private fun updatePoints() {
        lifecycleScope.launch {
            viewModel.updatePoints.collect {
                if (it) {
                    collectStates()
                    viewModel.updatePoints.value = false
                }
            }
        }

    }
}


