package com.example.lightlogisticsapp.shared

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lightlogisticsapp.model.*
import com.example.lightlogisticsapp.ui.shared.SharedViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedViewModelIntegrationTest {

    // Ensures LiveData updates synchronously during testing
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel

    @Before
    fun setUp() {
        // Initialize the SharedViewModel which acts as the central integration point
        viewModel = SharedViewModel()
    }

    @Test
    fun `should add order and update stock quantity when sufficient stock is available`() {
        // Validating the integration between order addition and stock management
        val newOrder = Order(
            id = "3",
            name = "Desk Chair",
            quantity = 10,
            price = 300.0,
            customerName = "Alice",
            deliveryDate = "2024-12-01"
        )

        // Add a new order
        val result = viewModel.addOrder(newOrder)

        // Check if the order was successfully added
        assertEquals(true, result)

        // Ensure stock quantity was reduced to reflect the processed order
        val updatedStocks = viewModel.stocks.value!!
        val updatedStock = updatedStocks.find { it.id == "1" } as Stock
        assertEquals(90, updatedStock.quantity)
    }

    @Test
    fun `should fail to add order when stock is insufficient`() {
        // Ensuring the system handles insufficient stock gracefully.
        val newOrder = Order(
            id = "4",
            name = "Desk Chair",
            quantity = 150, // Exceeds available stock
            price = 300.0,
            customerName = "Bob",
            deliveryDate = "2024-12-02"
        )

        // Attempt to add an order with insufficient stock
        val result = viewModel.addOrder(newOrder)

        // Assert: Ensure the order is rejected
        assertEquals(false, result)

        // Assert: Confirm that stock quantity remains unchanged
        val updatedStocks = viewModel.stocks.value!!
        val updatedStock = updatedStocks.find { it.id == "1" } as Stock
        assertEquals(100, updatedStock.quantity)
    }

    @Test
    fun `should update stock quantity correctly`() {
        // Verifying the stock management functionality in isolation.
        // Update stock quantity
        viewModel.updateStockQuantity("1", 80)

        // Assert: Validate the stock quantity is updated correctly
        val updatedStocks = viewModel.stocks.value!!
        val updatedStock = updatedStocks.find { it.id == "1" } as Stock
        assertEquals(80, updatedStock.quantity)
    }

    @Test
    fun `should update delivery status correctly`() {
        // Testing the delivery status workflow.
        // Update the status of an existing delivery
        viewModel.updateDeliveryStatus("1", DeliveryStatus.DELIVERED)

        // Assert: Ensure the delivery status is updated correctly
        val updatedDeliveries = viewModel.deliveries.value!!
        val updatedDelivery = updatedDeliveries.find { it.id == "1" }
        assertEquals(DeliveryStatus.DELIVERED, updatedDelivery?.status)
    }

    @Test
    fun `should add new delivery with correct items`() {
        // Validating the addition of deliveries and their linkage to items.
        val newDelivery = Delivery(
            id = "4",
            destination = "Customer C",
            status = DeliveryStatus.PENDING,
            items = listOf(
                Stock("3", "Keyboard", 50, 100.0)
            )
        )

        // Add a new delivery
        viewModel.addDelivery(newDelivery)

        // Assert: Check the delivery was added to the list
        val updatedDeliveries = viewModel.deliveries.value!!
        assertEquals(4, updatedDeliveries.size) // 3 initial deliveries + 1 new
        val addedDelivery = updatedDeliveries.find { it.id == "4" }
        assertEquals("Customer C", addedDelivery?.destination)
        assertEquals(DeliveryStatus.PENDING, addedDelivery?.status)
    }
}