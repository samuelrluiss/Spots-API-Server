package example.com.models

import kotlinx.serialization.Serializable

@Serializable
class Spot(
    val id: String,
    val userId: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val leavingAt: String,
    val createdAt: String,
    val status: String
)