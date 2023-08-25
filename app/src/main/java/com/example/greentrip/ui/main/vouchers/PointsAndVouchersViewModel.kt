package com.example.greentrip.ui.main.vouchers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greentrip.data.repository.UserRepo
import com.example.greentrip.utils.AuthState
import com.example.greentrip.utils.CountdownService
import com.example.greentrip.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointsAndVouchersViewModel@Inject constructor(private val userRepo: UserRepo) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    private val _stateVoucher = MutableStateFlow(AuthState())
    val stateVoucher = _stateVoucher.asSharedFlow()


    private val _stateDeleteVoucher = MutableStateFlow(AuthState())
    val stateDeleteVoucher = _stateDeleteVoucher.asSharedFlow()

    init {
        getProfile()
    }

    private fun getProfile() =
        viewModelScope.launch {
            userRepo.getProfile().collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            profile = it.data
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

     fun getSpecificVoucher(id:String) =
        viewModelScope.launch {
            userRepo.getSpecificVoucher(id).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateVoucher.value = _stateVoucher.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateVoucher.value = _stateVoucher.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            specificVoucher = it.data
                        )

                    }

                    is Status.Error -> {

                        _stateVoucher.value = _stateVoucher.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }

    fun startTimerWithId(id: String , context: Context) {
        context.startService( Intent(context, CountdownService::class.java).putExtra("ID_KEY", id))
    }

     fun deleteVoucher(id:String) =
        viewModelScope.launch {
            userRepo.deleteVoucher(id).collect {
                when (it) {
                    is Status.Loading -> {
                        _stateDeleteVoucher.value = _stateDeleteVoucher.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _stateDeleteVoucher.value = _stateDeleteVoucher.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            specificVoucher = it.data
                        )

                    }

                    is Status.Error -> {

                        _stateDeleteVoucher.value = _stateDeleteVoucher.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }
}