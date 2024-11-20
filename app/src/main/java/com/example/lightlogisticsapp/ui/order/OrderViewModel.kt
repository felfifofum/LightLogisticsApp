package com.example.lightlogisticsapp.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightlogisticsapp.model.Order
import com.example.lightlogisticsapp.ui.stock.StockViewModel

class OrderViewModel(private val stockViewModel: StockViewModel) : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    init {
        _orders.value = emptyList()
    }

    fun addOrder(newOrder: Order): Boolean {
        val stock = stockViewModel.stocks.value?.find { it.name.equals(newOrder.name, ignoreCase = true) }

        if (stock == null) {
            Log.d("OrderViewModel", "Stock not found for name: ${newOrder.name}")
            return false
        }

        if (stock.quantity < newOrder.quantity) {
            Log.d("OrderViewModel", "Not enough stock: Available=${stock.quantity}, Requested=${newOrder.quantity}")
            return false
        }

        Log.d("OrderViewModel", "Adding order: $newOrder")
        stockViewModel.updateStockQuantity(stock.id, stock.quantity - newOrder.quantity)

        _orders.value = _orders.value?.toMutableList()?.apply {
            add(newOrder)
        } ?: listOf(newOrder)

        return true
    }
}
