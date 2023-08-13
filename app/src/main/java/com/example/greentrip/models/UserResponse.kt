package com.example.greentrip.models

data class UserResponse(
    val `data`: Data?,
    val status: String?
) {
    data class Data(
        val `data`: Data?,
        val user: User?
    ) {
        data class Data(
            val __v: Int?,
            val _id: String?,
            val active: Boolean?,
            val avatar: String?,
            val bookings: List<Any?>?,
            val email: String?,
            val id: String?,
            val name: String?,
            val passwordChangedAt: String?,
            val points: Int?,
            val role: String?,
            val vouchers: List<Any?>?,
            val phone : String?
        )
        data class User(
            val __v: Int?,
            val _id: String?,
            val active: Boolean?,
            val avatar: String?,
            val email: String?,
            val id: String?,
            val name: String?,
            val passwordChangedAt: String?,
            val points: Int?,
            val phone : String?
        )
    }
}