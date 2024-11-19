package com.example.lightlogisticsapp.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightlogisticsapp.model.AbstractStock
import com.example.lightlogisticsapp.model.Delivery
import com.example.lightlogisticsapp.model.DeliveryStatus
import com.example.lightlogisticsapp.model.Order
import com.example.lightlogisticsapp.model.PerishableStock
import com.example.lightlogisticsapp.model.Stock

class SharedViewModel : ViewModel() {
    private val _stocks = MutableLiveData<List<AbstractStock<*>>>()
    val stocks: LiveData<List<AbstractStock<*>>> get() = _stocks

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    private val _deliveries = MutableLiveData<List<Delivery>>()
    val deliveries: LiveData<List<Delivery>> get() = _deliveries

    init {
        // Sample data
        _stocks.value = listOf(
            Stock("1", "Desk Chair", 100, 300.0),
            PerishableStock("2", "Milk", 50, 1.5, "11/12/2024")
        )
        _orders.value = listOf(
            Order("1", "Laptop", 10, 1500.0, "John Doe", "01/11/2024"),
            Order("2", "Smartphone", 20, 800.0, "Jane Smith", "02/11/2024")
        )
        _deliveries.value = listOf(
            Delivery(id = "1", destination = "Warehouse A", status = DeliveryStatus.PENDING, items = listOf())
        )
    }

    fun updateStockQuantity(id: String, newQuantity: Int) {
        _stocks.value = _stocks.value?.map {
            if (it.id == id) it.updateQuantity(newQuantity) else it
        }
    }

    fun addStock(newStock: AbstractStock<*>) {
        _stocks.value = _stocks.value?.toMutableList()?.apply {
            add(newStock)
        }
    }

    fun updateOrderQuantity(id: String, newQuantity: Int) {
        _orders.value = _orders.value?.map {
            if (it.id == id) it.updateQuantity(newQuantity) else it
        }
    }

    fun addOrder(newOrder: Order): Boolean {
        val stock = _stocks.value?.find { it.name == newOrder.name }
        return if (stock != null && stock.quantity >= newOrder.quantity) {
            updateStockQuantity(stock.id, stock.quantity - newOrder.quantity)
            _orders.value = _orders.value?.toMutableList()?.apply {
                add(newOrder)
            }
            true
        } else {
            false
        }
    }

    fun updateDeliveryStatus(id: String, newStatus: DeliveryStatus) {
        _deliveries.value = _deliveries.value?.map {
            if (it.id == id) it.updateStatus(newStatus) else it
        }
    }

    fun addDeliveryItem(id: String, item: Stock) {
        _deliveries.value = _deliveries.value?.map {
            if (it.id == id) it.addItem(item) else it
        }
    }

    fun addDelivery(newDelivery: Delivery) {
        _deliveries.value = _deliveries.value?.toMutableList()?.apply {
            add(newDelivery)
        }
    }
}