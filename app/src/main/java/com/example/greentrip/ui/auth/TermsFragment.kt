package com.example.greentrip.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.greentrip.R
import com.example.greentrip.databinding.FragmentTermsBinding
import dagger.hilt.android.AndroidEntryPoint


class TermsFragment : Fragment() {

    lateinit var binding: FragmentTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAccept.setOnClickListener {
            findNavController().navigate(R.id.action_termsFragment_to_welcomeFragment)
        }
    }


}