package com.example.lightlogisticsapp.model

interface StockOperations<T> {
    fun updateQuantity(newQuantity: Int): T
    fun updatePrice(newPrice: Double): T
}
