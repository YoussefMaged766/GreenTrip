package com.example.greentrip.models

data class UserResponse(
    val `data`: Data?,
    val status: String?
) {
    data class Data(
        val `data`: Data?
    ) {
        data class Data(
            val __v: Int?,
            val _id: String?,
            val active: Boolean?,
            val avatar: String?,
            val bookings: List<Booking?>?,
            val email: String?,
            val id: String?,
            val name: String?,
            val phone: String?,
            val points: Int?,
            val region: String?,
            val role: String?,
            val vouchers: List<Voucher?>?
        ) {
            data class Booking(
                val __v: Int?,
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

            data class Voucher(
                val __v: Int?,
                val _id: String?,
                val createdAt: String?,
                val paid: Boolean?,
                val reward: Reward?,
                val user: String?
            ) {
                data class Reward(
                    val _id: String?,
                    val costPoints: Int?,
                    val description: String?,
                    val pointOfInterest: PointOfInterest?,
                    val qrcode: String?,
                    val title: String?
                ) {
                    data class PointOfInterest(
                        val _id: String?,
                        val address: String?,
                        val agent: Agent?,
                        val availableTickets: Int?,
                        val costPoints: String?,
                        val description: String?,
                        val id: String?,
                        val name: String?,
                        val photo: String?,
                        val qrcode: String?
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
            }
        }
    }
}