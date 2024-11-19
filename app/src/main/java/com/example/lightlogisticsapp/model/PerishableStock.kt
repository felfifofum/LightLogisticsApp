package com.example.lightlogisticsapp.model

data class PerishableStock(
    override val id: String,
    override val name: String,
    override var quantity: Int,
    override var price: Double,
    val expirationDate: String // PerishableStock defining its own custom prop
    // Props inherited from AbstractStock
) : AbstractStock<PerishableStock>(id, name, quantity, price) {

    // Create new instance of PerishableStock with updated quantity
    override fun updateQuantity(newQuantity: Int): PerishableStock {
        return this.copy(quantity = newQuantity)
    }

    // Create new instance of PerishableStock with updated price
    override fun updatePrice(newPrice: Double): PerishableStock {
        return this.copy(price = newPrice)
    }

    /*
    Used to display visually in the app
    Full details of the employee's entered stock
    Returns output as string data type.
    */
    override fun displayStockInfo(): String {
        return "Stock ID: $id, Name: $name, Quantity: $quantity, Price: $price, Expiration Date: $expirationDate"
    }
}
