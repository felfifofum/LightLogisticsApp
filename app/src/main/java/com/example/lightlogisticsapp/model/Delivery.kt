package com.example.lightlogisticsapp.model

// Enum for delivery status
enum class DeliveryStatus {
    PENDING,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED
}

// Delivery class
data class Delivery(
    val id: String,
    val destination: String,
    var status: DeliveryStatus,
    val items: List<AbstractStock<*>>
) {
    fun updateStatus(newStatus: DeliveryStatus): Delivery {
        return this.copy(status = newStatus)
    }

    fun addItem(item: Stock): Delivery {
        val updatedItems = items.toMutableList().apply { add(item) }
        return this.copy(items = updatedItems)
    }
}