package com.example.greentrip.ui.main.vouchers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.greentrip.data.repository.UserRepo
import com.example.greentrip.utils.AuthState
import com.example.greentrip.utils.CountdownService
import com.example.greentrip.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class PointsAndVouchersViewModel @Inject constructor(private val userRepo: UserRepo, context: Context) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    private val _stateVoucher = MutableStateFlow(AuthState())
    val stateVoucher = _stateVoucher.asSharedFlow()


    private val _stateDeleteVoucher = MutableStateFlow(AuthState())
    val stateDeleteVoucher = _stateDeleteVoucher.asStateFlow()


    private lateinit var broadcastReceiver: BroadcastReceiver
    private val contextRef: WeakReference<Context> = WeakReference(context)

    init {
        getProfile()
        setupBroadcastReceiver()
    }

    private fun setupBroadcastReceiver() {
        val context = contextRef.get()

        if (context != null) {
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val id = intent?.getStringExtra("ID_KEY")
                    id?.let {
                        viewModelScope.launch {
                            deleteVoucher(id)
                            Toast.makeText(context, "Deleted Success", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            val intentFilter = IntentFilter("CountdownFinished")
            LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter)
        }
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

    fun getSpecificVoucher(id: String) =
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

    fun startTimerWithId(id: String, context: Context) {
        context.startService(Intent(context, CountdownService::class.java).putExtra("ID_KEY", id))
    }


    suspend fun deleteVoucher(id: String) =

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
                    Log.e("deleteVoucher: ", it.data.toString())

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

    override fun onCleared() {
        super.onCleared()
        val context = contextRef.get()

        if (context != null) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver)
        }
    }

}