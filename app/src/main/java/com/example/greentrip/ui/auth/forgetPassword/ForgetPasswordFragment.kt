package com.example.greentrip.ui.auth.forgetPassword

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
import com.example.greentrip.databinding.FragmentForgetPasswordBinding
import com.example.greentrip.models.AuthModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {

    lateinit var binding: FragmentForgetPasswordBinding
    val viewModel by viewModels<ForgetPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStates()

        binding.btnLogin.setOnClickListener {
            callApi()
        }

    }

    private fun callApi() {
        viewModel.forgotPassword(
            AuthModel(
                email = binding.txtEmail.text.toString().trim(),
            )
        )
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
                        findNavController().navigate(R.id.action_forgetPasswordFragment_to_recoverFragment)
                    }
                }

        }
    }


}