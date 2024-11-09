package com.example.lightlogisticsapp.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightlogisticsapp.model.Order
import com.example.lightlogisticsapp.model.Stock
import com.example.lightlogisticsapp.ui.stock.StockViewModel

class OrderViewModel(private val stockViewModel: StockViewModel) : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    init {
        // Sample data
        _orders.value = listOf(
            Order("1", "Laptop", 10, 1500.0, "John Doe", "01/11/2024"),
            Order("2", "Smartphone", 20, 800.0, "Jane Smith", "02/11/2024")
        )
    }

    fun updateOrderQuantity(id: String, newQuantity: Int) {
        _orders.value = _orders.value?.map {
            if (it.id == id) it.updateQuantity(newQuantity) else it
        }
    }

    fun addOrder(newOrder: Order): Boolean {
        val stock = stockViewModel.stocks.value?.find { it.name == newOrder.name }
        return if (stock != null && stock.quantity >= newOrder.quantity) {
            stockViewModel.updateStockQuantity(stock.id, stock.quantity - newOrder.quantity)
            _orders.value = _orders.value?.toMutableList()?.apply {
                add(newOrder)
            }
            true
        } else {
            false
        }
    }
}
