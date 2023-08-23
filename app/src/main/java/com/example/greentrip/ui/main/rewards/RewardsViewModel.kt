package com.example.greentrip.ui.main.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greentrip.data.repository.UserRepo
import com.example.greentrip.models.BookingModel
import com.example.greentrip.utils.AuthState
import com.example.greentrip.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RewardsViewModel@Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    private val _stateReward = MutableStateFlow(AuthState())
    val stateReward = _stateReward.asSharedFlow()

    private val _statePoint = MutableStateFlow(AuthState())
    val statePoint = _statePoint.asSharedFlow()

    init {
        getAllRewards()
    }


    private fun getAllRewards() =
        viewModelScope.launch {
            userRepo.getAllRewards().collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            allRewards = it.data

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

     fun getSpecificReward(id:String) =
        viewModelScope.launch {
            userRepo.getSpecificReward(id).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateReward.value = _stateReward.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateReward.value = _stateReward.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            reward = it.data

                        )

                    }

                    is Status.Error -> {

                        _stateReward.value = _stateReward.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }


    fun createVoucher(booking: BookingModel) =
        viewModelScope.launch {
            userRepo.createVoucher(booking).collect {
                when (it) {
                    is Status.Loading -> {
                        _statePoint.value = _statePoint.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _statePoint.value = _statePoint.value.copy(
                            isLoading = false,
                            status = it.data.status.toString()

                        )

                    }

                    is Status.Error -> {

                        _statePoint.value = _statePoint.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }
}