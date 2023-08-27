package com.example.greentrip.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.example.greentrip.R
import com.example.greentrip.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
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
        binding.imgHome.setOnClickListener {
            val navController = findNavController(this, R.id.nav_host_fragment)
            navController.navigate(R.id.homeFragment)
        }

        binding.btnPoints.setOnClickListener {
            val navController = findNavController(this, R.id.nav_host_fragment)
            navController.navigate(R.id.pointsAndVouchersFragment)
        }


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


