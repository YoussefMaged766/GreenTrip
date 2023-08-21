package com.example.greentrip.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.greentrip.R
import com.example.greentrip.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        binding.cardPointsOfInterest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mainPointsFragment)
        }

        binding.cardRewards.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_rewardsFragment)
        }


    }


}