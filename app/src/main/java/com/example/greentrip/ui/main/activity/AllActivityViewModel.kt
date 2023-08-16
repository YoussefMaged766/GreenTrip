package com.example.greentrip.ui.main.activity

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
class AllActivityViewModel@Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    private val _stateActivity = MutableStateFlow(AuthState())
    val stateActivity = _stateActivity.asSharedFlow()

    private val _stateBooking = MutableStateFlow(AuthState())
    val stateBooking = _stateBooking.asSharedFlow()

    fun getActivity(id:String) =
        viewModelScope.launch {
            userRepo.getActivity(id).collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            activity = it.data

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

    fun booking(booking: BookingModel) =
        viewModelScope.launch {
            userRepo.booking(booking).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateBooking.value = _stateBooking.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateBooking.value = _stateBooking.value.copy(
                            isLoading = false,
                            status = it.data.status.toString()


                        )

                    }

                    is Status.Error -> {

                        _stateBooking.value = _stateBooking.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }

    fun getSpecificActivity(id:String) =
        viewModelScope.launch {
            userRepo.getSpecificActivity(id).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateActivity.value = _stateActivity.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateActivity.value = _stateActivity.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            specificPoint = it.data

                        )

                    }

                    is Status.Error -> {

                        _stateActivity.value = _stateActivity.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }
}