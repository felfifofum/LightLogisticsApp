package com.example.lightlogisticsapp

import android.app.Application
import com.example.lightlogisticsapp.model.Stock

import com.tealium.core.Environment
import com.tealium.core.LogLevel
import com.tealium.core.Tealium
import com.tealium.core.TealiumConfig
import com.tealium.dispatcher.TealiumEvent

object TealiumHelper {

    lateinit var tealium: Tealium

    fun init(application: Application) {
        val tealiumConfig = TealiumConfig(
            application,
            "LightLogistics",
            "ll-lightlogisticsapp",
            Environment.DEV,
        )
        tealium = Tealium.create("tealium_instance", tealiumConfig)
    }

    fun trackEvent(eventName: String, data: Map<String, Any>? = null) {
        tealium.track(TealiumEvent(eventName, data))
    }

    fun trackAppLaunch() {
        trackEvent("app_launch", mapOf("app_name" to "LightLogisticsApp"))
    }

    // Track add stock event with custom data
    fun trackStockAdd(stock: Stock) {
        TealiumHelper.trackEvent("add_stock", mapOf(
            "stock_id" to stock.id,
            "stock_name" to stock.name,
            "stock_quantity" to stock.quantity.toString(),
            "stock_price" to stock.price.toString()
        ))
    }

    fun trackStockUpdate(stockId: String, newQuantity: Int) {
        TealiumHelper.trackEvent("update_stock", mapOf(
            "stock_id" to stockId,
            "new_quantity" to newQuantity.toString()
        ))
    }
}