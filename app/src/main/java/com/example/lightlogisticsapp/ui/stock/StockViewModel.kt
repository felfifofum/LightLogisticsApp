package com.example.lightlogisticsapp.ui.stock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightlogisticsapp.model.Stock

class StockViewModel : ViewModel() {

    private val _stock = MutableLiveData<Stock>().apply {
        value = Stock(id = "1", name = "Sample Item", quantity = 100, price = 10.0)
    }
    val stock: LiveData<Stock> = _stock

    private val _events = MutableLiveData<String>()
    val events: LiveData<String> = _events

    fun updateStockQuantity(newQuantity: Int) {
        _stock.value = _stock.value?.updateQuantity(newQuantity)
        _events.value = "Stock quantity updated to $newQuantity"
    }

    fun updateStockPrice(newPrice: Double) {
        _stock.value = _stock.value?.updatePrice(newPrice)
        _events.value = "Stock price updated to $newPrice"
    }

    fun addNewStock(stock: Stock) {
        _stock.value = stock
        _events.value = "New stock added: ${stock.name}"
    }

    fun placeOrder(quantity: Int) {
        _stock.value?.let {
            if (it.quantity >= quantity) {
                _stock.value = it.updateQuantity(it.quantity - quantity)
                _events.value = "Order placed for $quantity items"
            } else {
                _events.value = "Insufficient stock to place order"
            }
        }
    }
}
