package com.example.greentrip.models

data class VoucherResponse(
    val `data`: Data?,
    val status: String?
) {
    data class Data(
        val voucher: Voucher?
    ) {
        data class Voucher(
            val __v: Int?,
            val _id: String?,
            val createdAt: String?,
            val paid: Boolean?,
            val reward: String?,
            val user: String?
        )
    }
}