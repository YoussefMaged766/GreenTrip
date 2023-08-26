package com.example.greentrip.utils

import com.example.greentrip.models.ActivityResponse
import com.example.greentrip.models.LoginResponse
import com.example.greentrip.models.PointsResponse
import com.example.greentrip.models.ReservationResponse
import com.example.greentrip.models.RewardResponse
import com.example.greentrip.models.SpecificPointResponse
import com.example.greentrip.models.SpecificReservationResponse
import com.example.greentrip.models.SpecificRewardResponse
import com.example.greentrip.models.SpecificVoucherResponse
import com.example.greentrip.models.UserResponse
import retrofit2.Response

data class AuthState(
    val isLoading: Boolean = false,
    val status: String? = null,
    val userLogin: LoginResponse? = null,
    val profile: UserResponse? = null,
    val points: PointsResponse? = null,
    val specificPoint: SpecificPointResponse? = null,
    val activity: ActivityResponse? = null,
    val allRewards: RewardResponse? = null,
    val reward: SpecificRewardResponse? = null,
    val reservation: ReservationResponse? = null,
    val specificReservation: SpecificReservationResponse? = null,
    val specificVoucher: SpecificVoucherResponse? = null,
)
