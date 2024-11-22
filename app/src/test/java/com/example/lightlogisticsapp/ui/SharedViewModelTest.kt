package com.example.lightlogisticsapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lightlogisticsapp.model.Delivery
import com.example.lightlogisticsapp.model.DeliveryStatus
import com.example.lightlogisticsapp.model.Stock
import com.example.lightlogisticsapp.ui.shared.SharedViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel

    @Before
    fun setUp() {
        viewModel = SharedViewModel()
    }

    @Test
    fun `test adding a new delivery updates the list`() {
        val delivery = Delivery(id = "1", destination = "Warehouse A", status = DeliveryStatus.PENDING, items = listOf(Stock("1", "Desk Chair", 100, 300.0)))

        // Add delivery
        viewModel.addDelivery(delivery)

        // Observe LiveData
        val deliveries = viewModel.deliveries.value

        // Assert delivery list contains the new added delivery
        assertEquals(4, deliveries?.size)
        assertEquals(delivery, deliveries?.first())
    }
}