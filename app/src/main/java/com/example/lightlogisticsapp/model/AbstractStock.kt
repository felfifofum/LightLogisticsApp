package com.example.lightlogisticsapp.model

abstract class AbstractStock<T : AbstractStock<T>>(
    open val id: String,
    open val name: String,
    open var quantity: Int,
    open var price: Double
) : StockOperations<T> {
    abstract fun displayStockInfo(): String
}
