package com.example.greentrip.ui.auth.recover

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.greentrip.R
import com.example.greentrip.databinding.FragmentRecoverBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecoverFragment : Fragment() {

    lateinit var binding: FragmentRecoverBinding
    val viewModel by viewModels<RecoverViewModel>()

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
        binding  = FragmentRecoverBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStates()

        binding.btnRecover.setOnClickListener {
            viewModel.verifyCode(binding.txtCode.text.toString().trim())
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

                    if (!it.isLoading && it.status != null) {
                        Toast.makeText(requireContext(), it.status, Toast.LENGTH_SHORT).show()
                    }

                    if (!it.isLoading && it.status == "success") {
                        val action = RecoverFragmentDirections.actionRecoverFragmentToResetPasswordFragment(binding.txtCode.text.toString().trim())
                        findNavController().navigate(action)
                    }
                }

        }
    }



}