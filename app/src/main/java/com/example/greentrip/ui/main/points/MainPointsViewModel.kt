package com.example.greentrip.ui.main.points

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
class MainPointsViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    private val _statePoint = MutableStateFlow(AuthState())
    val statePoint = _statePoint.asSharedFlow()

    private val _stateBooking = MutableStateFlow(AuthState())
    val stateBooking = _stateBooking.asSharedFlow()

    private val _stateAddPoint = MutableStateFlow(AuthState())
    val stateAddPoint = _stateAddPoint.asSharedFlow()

    init {
        getAllPoints()
    }

    private fun getAllPoints() =
        viewModelScope.launch {
            userRepo.getAllPoints().collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            points = it.data

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

    fun getSpecificPoints(id: String) =
        viewModelScope.launch {
            userRepo.getSpecificPoint(id).collect {
                when (it) {
                    is Status.Loading -> {
                        _statePoint.value = _statePoint.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _statePoint.value = _statePoint.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            specificPoint = it.data

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

    fun addPoint(booking: BookingModel) =
        viewModelScope.launch {
            userRepo.addPoint(booking).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateAddPoint.value = _stateAddPoint.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateAddPoint.value = _stateAddPoint.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            activity = it.data

                        )

                    }

                    is Status.Error -> {

                        _stateAddPoint.value = _stateAddPoint.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }


}