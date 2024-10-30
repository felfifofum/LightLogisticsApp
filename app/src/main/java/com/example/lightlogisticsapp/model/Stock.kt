package com.example.lightlogisticsapp.model

data class Stock(
    override val id: String,
    override val name: String,
    override var quantity: Int,
    override var price: Double
) : AbstractStock<Stock>(id, name, quantity, price) {
    override fun updateQuantity(newQuantity: Int): Stock {
        return this.copy(quantity = newQuantity)
    }

    override fun updatePrice(newPrice: Double): Stock {
        return this.copy(price = newPrice)
    }

    override fun displayStockInfo(): String {
        return "Stock ID: $id, Name: $name, Quantity: $quantity, Price: $price"
    }
}
