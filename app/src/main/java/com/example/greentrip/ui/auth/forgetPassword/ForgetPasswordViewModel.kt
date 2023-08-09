package com.example.greentrip.ui.auth.forgetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greentrip.data.repository.AuthRepo
import com.example.greentrip.models.AuthModel
import com.example.greentrip.utils.AuthState
import com.example.greentrip.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val userRepo: AuthRepo) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    fun forgotPassword(user: AuthModel) =
        viewModelScope.launch {
            userRepo.forgotPassword(user).collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString()

                        )

                    }

                    is Status.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }

}