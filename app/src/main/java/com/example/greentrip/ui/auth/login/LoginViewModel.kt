package com.example.greentrip.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greentrip.data.repository.AuthRepo
import com.example.greentrip.models.LoginResponse
import com.example.greentrip.models.AuthModel
import com.example.greentrip.utils.AuthState
import com.example.greentrip.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: AuthRepo) : ViewModel() {


    private val _stateLoad = MutableStateFlow(true)
    val stateLoad = _stateLoad.asStateFlow()

    private val _stateStatus = MutableStateFlow(LoginResponse())
    val stateStatus = _stateStatus.asStateFlow()

    private val _stateMessage = MutableStateFlow("")
    val stateMessage = _stateMessage.asStateFlow()

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()


    fun loginUser(user: AuthModel) =
        viewModelScope.launch {
            userRepo.loginUser(user).collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            userLogin = it.data
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


