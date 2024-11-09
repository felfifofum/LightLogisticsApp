package com.example.lightlogisticsapp.model

data class Order(
    override val id: String,
    override val name: String,
    override var quantity: Int,
    override var price: Double,
    val customerName: String,
    val deliveryDate: String
) : AbstractStock<Order>(id, name, quantity, price) {

    override fun updateQuantity(newQuantity: Int): Order {
        return this.copy(quantity = newQuantity)
    }

    override fun updatePrice(newPrice: Double): Order {
        return this.copy(price = newPrice)
    }

    override fun displayStockInfo(): String {
        return "Order ID: $id, Name: $name, Quantity: $quantity, Price: $price, Customer: $customerName, Delivery Date: $deliveryDate"
    }
}
