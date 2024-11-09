package com.example.lightlogisticsapp.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightlogisticsapp.model.Delivery
import com.example.lightlogisticsapp.model.DeliveryStatus
import com.example.lightlogisticsapp.model.Stock

class DeliveryViewModel : ViewModel() {

    private val _deliveries = MutableLiveData<List<Delivery>>().apply {
        value = listOf(
            Delivery(id = "1", destination = "Warehouse A", status = DeliveryStatus.PENDING, items = listOf())
        )
    }
    val deliveries: LiveData<List<Delivery>> = _deliveries

    private val _events = MutableLiveData<String>()
    val events: LiveData<String> = _events

    fun updateDeliveryStatus(id: String, newStatus: DeliveryStatus) {
        _deliveries.value = _deliveries.value?.map {
            if (it.id == id) it.updateStatus(newStatus) else it
        }
        _events.value = "Delivery status updated to $newStatus"
    }

    fun addDeliveryItem(id: String, item: Stock) {
        _deliveries.value = _deliveries.value?.map {
            if (it.id == id) it.addItem(item) else it
        }
        _events.value = "Item added to delivery: ${item.name}"
    }

    fun addDelivery(newDelivery: Delivery) {
        _deliveries.value = _deliveries.value?.toMutableList()?.apply {
            add(newDelivery)
        }
        _events.value = "New delivery added: ${newDelivery.id}"
    }
}
