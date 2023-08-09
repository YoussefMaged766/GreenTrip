package com.example.greentrip.ui.auth.register

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
class SignUpViewModel  @Inject constructor(private val userRepo: AuthRepo) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    fun registerUser(user: AuthModel) =
        viewModelScope.launch {
            userRepo.registerUser(user).collect {
                when (it) {
                    is Status.Loading -> {
//                   _stateLoad.value = true
//                         _state.update {state->
//                             state.copy(isLoading = true)
//
//                         }
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {
//                   _stateLoad.value = false
//                   _stateStatus.value = it.data
//                   _stateMessage.value = it.data.status.toString()
//                         _state.update {state->
//                             state.copy(isLoading = false,
//                                 status = state.status.toString(),
//                                 userLogin = state.userLogin
//
//                             )
//
//                         }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            userLogin = it.data
                        )

                    }

                    is Status.Error -> {
//                   _stateLoad.value = false
//                   _stateMessage.value = it.message
//                        _state.update { state ->
//                            state.copy(
//                                isLoading = false,
//                                status = state.status.toString()
//                            )
//                        }

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