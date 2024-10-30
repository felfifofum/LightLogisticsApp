package com.example.lightlogisticsapp.model

data class Stock(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Double
) {
    fun updateQuantity(newQuantity: Int): Stock {
        return this.copy(quantity = newQuantity)
    }

    fun updatePrice(newPrice: Double): Stock {
        return this.copy(price = newPrice)
    }
}
