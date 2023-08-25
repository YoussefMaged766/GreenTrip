package com.example.greentrip.models

data class ReservationResponse(
    val `data`: List<Data>?,
    val result: Int?,
    val status: String?,

) {
    data class Data(
        val _id: String?,
        val activity: Activity?,
        val createdAt: String?,
        val id: String?,
        val numOfDays: Int?,
        val numOfTickets: Int?,
        val paid: Boolean?,
        val point: Point?,
        val status: String?,
        val type: String?,
        val user: User?
    ) {
        data class Activity(
            val _id: String?,
            val description: String?,
            val id: String?,
            val name: String?,
            val photo: String?,
            val pointOfInterest: PointOfInterest?,
            val reservationLimit: Int?,
            val startAt: String?
        ) {
            data class PointOfInterest(
                val _id: String?,
                val address: String?,
                val agent: Agent?,
                val availableTickets: Int?,
                val category: String?,
                val costPoints: String?,
                val description: String?,
                val id: String?,
                val name: String?,
                val photo: String?,
                val qrcode: String?,
                val region: String?,
                val slug: String?
            ) {
                data class Agent(
                    val _id: String?,
                    val email: String?,
                    val id: String?,
                    val name: String?,
                    val phone: String?
                )
            }
        }

        data class Point(
            val _id: String?,
            val address: String?,
            val agent: Agent?,
            val availableTickets: Int?,
            val category: String?,
            val costPoints: String?,
            val description: String?,
            val id: String?,
            val name: String?,
            val photo: String?,
            val qrcode: String?,
            val region: String?,
            val slug: String?
        ) {
            data class Agent(
                val _id: String?,
                val email: String?,
                val id: String?,
                val name: String?,
                val phone: String?
            )
        }

        data class User(
            val _id: String?,
            val avatar: String?,
            val email: String?,
            val id: String?,
            val name: String?,
            val phone: String?,
            val region: String?
        )
    }
}