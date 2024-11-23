package com.example.lightlogisticsapp.ui.order

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lightlogisticsapp.model.Order
import com.example.lightlogisticsapp.ui.stock.StockViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Unit test
/*
    Running test suite with
    Robolectric to simulate an Android environment
*/
@RunWith(RobolectricTestRunner::class)
class OrderViewModelTest {

    // Rule to allow LiveData to work synchronously in the test environment
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock the data
    private val stockViewModel = StockViewModel()
    // Dependency injection of StockViewModel
    private val orderViewModel = OrderViewModel(stockViewModel)

    @Test
    fun `should initialise stocks correctly`() {
        // Fetch stock data from LiveData
        val stocks = stockViewModel.stocks.value
        assertEquals(2, stocks?.size)
    }

    @Test
    fun `should add order when stock is available`() {
        // Ficticious instance
        val order = Order(
            id = "1",
            name = "Milk",
            quantity = 1,
            price = 10.0,
            customerName = "Customer",
            deliveryDate = "01/01/2025"
        )
        // Attempt to add  order
        val result = orderViewModel.addOrder(order)
        assertEquals(true, result)
    }

    @Test
    fun `should not add order when stock is insufficient`() {
        val order = Order(
            id = "1",
            name = "item",
            quantity = 100,
            price = 10.0,
            customerName = "Customer",
            deliveryDate = "01/01/2025"
        )
        val result = orderViewModel.addOrder(order)
        assertEquals(false, result)
    }
}