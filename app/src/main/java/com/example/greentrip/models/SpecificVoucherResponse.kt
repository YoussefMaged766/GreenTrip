package com.example.greentrip.models

data class SpecificVoucherResponse(
    val `data`: Data?,
    val status: String?
) {
    data class Data(
        val `data`: Data?
    ) {
        data class Data(
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
                    val qrcode: String?,
                    val region: String?
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