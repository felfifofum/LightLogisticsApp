package com.example.lightlogisticsapp.model

data class Delivery(
    val id: String,
    val destination: String,
    val status: String,
    val items: List<Stock>
) {
    fun updateStatus(newStatus: String): Delivery {
        return this.copy(status = newStatus)
    }
}
