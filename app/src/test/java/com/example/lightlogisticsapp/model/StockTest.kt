package com.example.lightlogisticsapp.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test

class StockTest {

    // It should update stock qty and return new instance with updated value.
    @Test
    fun `updateQuantity should return a new Stock with updated quantity`() {

        val stock = Stock(id = "1", name = "Desk Chair", quantity = 100, price = 300.0)
        val updatedStock = stock.updateQuantity(120)

        // Ensure it returns new instance
        assertNotSame(stock, updatedStock)
        //Expect quantity to update
        assertEquals(120, updatedStock.quantity)
    }

    @Test
    fun `updateQuantity should return a new PerishableStock with updated quantity`() {

        val perishableStock = PerishableStock(id = "2", name = "Milk", quantity = 50, price = 1.5, expirationDate = "11/12/2024")
        val updatedPerishableStock = perishableStock.updateQuantity(60)

        assertNotSame(perishableStock, updatedPerishableStock)
        assertEquals(60, updatedPerishableStock.quantity)
    }

    // Test the updatePrice method

    /*
    * This updates the price of the stock and
    * returns a new instance with
    * the updated value.
    */
    @Test
    fun `updatePrice should return a new Stock with updated price`() {
        // Arrange
        val stock = Stock(id = "1", name = "Desk Chair", quantity = 100, price = 300.0)

        // Act
        val updatedStock = stock.updatePrice(350.0)

        // Assert
        assertNotSame(stock, updatedStock)
        assertEquals(350.0, updatedStock.price, 0.0)
    }

    @Test
    fun `updatePrice should return a new PerishableStock with updated price`() {

        val perishableStock = PerishableStock(id = "2", name = "Milk", quantity = 50, price = 1.5, expirationDate = "11/12/2024")
        val updatedPerishableStock = perishableStock.updatePrice(2.0)

        assertNotSame(perishableStock, updatedPerishableStock)
        assertEquals(2.0, updatedPerishableStock.price, 0.0)
    }

    // Test the displayStockInfo method
    @Test
    fun `displayStockInfo should return correct string for Stock`() {

        val stock = Stock(id = "1", name = "Desk Chair", quantity = 100, price = 300.0)
        val stockInfo = stock.displayStockInfo()

        assertEquals("Stock ID: 1, Name: Desk Chair, Quantity: 100, Price: 300.0", stockInfo)
    }

    @Test
    fun `displayStockInfo should return correct string for PerishableStock`() {

        val perishableStock = PerishableStock(id = "2", name = "Milk", quantity = 50, price = 1.5, expirationDate = "11/12/2024")
        val perishableStockInfo = perishableStock.displayStockInfo()

        // Assertation
        assertEquals("Stock ID: 2, Name: Milk, Quantity: 50, Price: 1.5, Expiration Date: 11/12/2024", perishableStockInfo)
    }
}