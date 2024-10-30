package com.example.lightlogisticsapp.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeliveryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is delivery Fragment"
    }
    val text: LiveData<String> = _text
}