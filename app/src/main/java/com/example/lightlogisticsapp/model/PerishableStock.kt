package com.example.lightlogisticsapp.model

data class PerishableStock(
    override val id: String,
    override val name: String,
    override var quantity: Int,
    override var price: Double,
    val expirationDate: String
) : AbstractStock<PerishableStock>(id, name, quantity, price) {
    override fun updateQuantity(newQuantity: Int): PerishableStock {
        return this.copy(quantity = newQuantity)
    }

    override fun updatePrice(newPrice: Double): PerishableStock {
        return this.copy(price = newPrice)
    }

    override fun displayStockInfo(): String {
        return "Stock ID: $id, Name: $name, Quantity: $quantity, Price: $price, Expiration Date: $expirationDate"
    }
}
