package com.example.lightlogisticsapp.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightlogisticsapp.model.Delivery
import com.example.lightlogisticsapp.model.Stock

class DeliveryViewModel : ViewModel() {

    private val _delivery = MutableLiveData<Delivery>().apply {
        value = Delivery(id = "1", destination = "Warehouse A", status = "Pending", items = listOf())
    }
    val delivery: LiveData<Delivery> = _delivery

    private val _events = MutableLiveData<String>()
    val events: LiveData<String> = _events

    fun updateDeliveryStatus(newStatus: String) {
        _delivery.value = _delivery.value?.updateStatus(newStatus)
        _events.value = "Delivery status updated to $newStatus"
    }

    fun addDeliveryItem(item: Stock) {
        _delivery.value?.let {
            val updatedItems = it.items.toMutableList().apply { add(item) }
            _delivery.value = it.copy(items = updatedItems)
            _events.value = "Item added to delivery: ${item.name}"
        }
    }
}
