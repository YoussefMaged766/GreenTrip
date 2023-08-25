package com.example.greentrip.ui.main.reservation

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
class ReservationViewModel@Inject constructor(private val userRepo: UserRepo) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    private val _stateBooking = MutableStateFlow(AuthState())
    val stateBooking = _stateBooking.asSharedFlow()


    private val _stateCancelBooking = MutableStateFlow(AuthState())
    val stateCancelBooking = _stateCancelBooking.asSharedFlow()




    fun getAllBooking() =
        viewModelScope.launch {
            userRepo.getAllBooking().collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            reservation = it.data

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

     fun getSpecificBooking(id:String) =
        viewModelScope.launch {
            userRepo.getSpecificBooking(id).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateBooking.value = _stateBooking.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateBooking.value = _stateBooking.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            specificReservation = it.data

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

    fun cancelBooking(booking: BookingModel ,id:String) =
        viewModelScope.launch {
            userRepo.cancelBooking(booking,id).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateCancelBooking.value = _stateCancelBooking.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateCancelBooking.value = _stateCancelBooking.value.copy(
                            isLoading = false,
                            status = it.data.status.toString()


                        )

                    }

                    is Status.Error -> {

                        _stateCancelBooking.value = _stateCancelBooking.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }
}