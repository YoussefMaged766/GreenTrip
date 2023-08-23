package com.example.greentrip.models

data class RewardResponse(
    val data: List<Data>?,
    val result: Int?,
    val status: String?
) {
    data class Data(
        val _id: String?,
        val costPoints: Int?,
        val description: String?,
        val expireDate: String?,
//        val pointOfInterest: String?,
        val qrcode: String?,
        val title: String?
    )
}