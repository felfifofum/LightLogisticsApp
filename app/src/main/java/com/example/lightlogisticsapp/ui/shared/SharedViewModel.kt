package com.example.lightlogisticsapp.ui.shared

import android.util.Log
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
            Delivery(
                id = "1",
                destination = "Warehouse A",
                status = DeliveryStatus.PENDING,
                items = listOf(
                    Stock("1", "Desk Chair", 100, 300.0)
                )
            ),
            Delivery(
                id = "2",
                destination = "Customer B",
                status = DeliveryStatus.IN_TRANSIT,
                items = listOf(
                    PerishableStock("4", "Milk", 50, 1.5, "11/12/2024")
                )
            ),
            Delivery(
                id = "3",
                destination = "Main Office",
                status = DeliveryStatus.DELIVERED,
                items = listOf(
                    Stock("2", "Laptop", 50, 1500.0)
                )
            )
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
        Log.d("SharedViewModel", "Available stocks: ${_stocks.value}")

        // Case-insensitive/trimmed allowance
        val stock = _stocks.value?.find {
            it.name.trim().equals(newOrder.name.trim(), ignoreCase = true)
        }

        if (stock == null) {
            Log.d("SharedViewModel", "Stock not found for name: ${newOrder.name.trim()}")
            return false
        }

        if (stock.quantity < newOrder.quantity) {
            Log.d("SharedViewModel", "Not enough stock: Available=${stock.quantity}, Requested=${newOrder.quantity}")
            return false
        }

        // Stock found and sufficient quantity available
        updateStockQuantity(stock.id, stock.quantity - newOrder.quantity)

        // Update orders list
        _orders.value = _orders.value?.toMutableList()?.apply {
            add(newOrder)
        } ?: listOf(newOrder)

        return true
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