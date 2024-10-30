package com.example.lightlogisticsapp.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is order Fragment"
    }
    val text: LiveData<String> = _text

    // Additional LiveData for order details
    private val _orderDetails = MutableLiveData<String>()
    val orderDetails: LiveData<String> = _orderDetails

    // Method to update order details
    fun updateOrderDetails(details: String) {
        _orderDetails.value = details
    }
}
