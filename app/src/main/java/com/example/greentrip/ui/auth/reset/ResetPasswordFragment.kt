package com.example.greentrip.ui.auth.reset

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
import androidx.navigation.fragment.navArgs
import com.example.greentrip.R
import com.example.greentrip.databinding.FragmentResetPasswordBinding
import com.example.greentrip.models.AuthModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel by viewModels<ResetPasswordViewModel>()
    private val token :ResetPasswordFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectStates()

        binding.btnRecover.setOnClickListener {
            viewModel.resetPassword(token.token,AuthModel(
                password = binding.txtPassword.text.toString().trim(),
                passwordConfirm = binding.txtPassword.text.toString().trim()
            ))
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
                        findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
                    }
                }

        }
    }


}