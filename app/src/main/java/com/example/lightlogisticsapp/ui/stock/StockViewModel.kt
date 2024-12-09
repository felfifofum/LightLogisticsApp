package com.example.lightlogisticsapp.ui.stock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightlogisticsapp.model.AbstractStock
import com.example.lightlogisticsapp.model.PerishableStock
import com.example.lightlogisticsapp.model.Stock

class StockViewModel : ViewModel() {
    private val _stocks = MutableLiveData<List<AbstractStock<*>>>()
    val stocks: LiveData<List<AbstractStock<*>>> get() = _stocks

    init {
        // Sample data
        _stocks.value = listOf(
            Stock("1", "Desk Chair", 100, 300.0),
            PerishableStock("2", "Milk", 50, 1.5, "30/07/2025")
        )
    }

    fun updateStockQuantity(id: String, newQuantity: Int) {
        _stocks.value = _stocks.value?.map {
            if (it.id == id) it.updateQuantity(newQuantity) else it
            if (it.id == id) it.updateQuantity(newQuantity) else it
        }
    }
}
