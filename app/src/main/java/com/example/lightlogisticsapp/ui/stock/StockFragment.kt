package com.example.lightlogisticsapp.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightlogisticsapp.R
import com.example.lightlogisticsapp.model.Stock
import com.example.lightlogisticsapp.viewmodel.StockViewModel

class StockFragment : Fragment() {

    private val stockViewModel: StockViewModel by viewModels()
    private lateinit var stockAdapter: StockAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stockAdapter = StockAdapter { stock, newQuantity ->
            stockViewModel.updateStockQuantity(stock.id, newQuantity)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = stockAdapter

        stockViewModel.stocks.observe(viewLifecycleOwner, Observer { stocks ->
            stockAdapter.submitList(stocks)
        })

        val addStockButton = view.findViewById<Button>(R.id.add_stock_button)
        val editStockName = view.findViewById<EditText>(R.id.edit_stock_name)
        val editStockQuantity = view.findViewById<EditText>(R.id.edit_stock_quantity)
        val editStockPrice = view.findViewById<EditText>(R.id.edit_stock_price)

        addStockButton.setOnClickListener {
            val name = editStockName.text.toString()
            val quantity = editStockQuantity.text.toString().toIntOrNull() ?: 0
            val price = editStockPrice.text.toString().toDoubleOrNull() ?: 0.0

            if (name.isNotEmpty()) {
                val newStock = Stock(id = System.currentTimeMillis().toString(), name = name, quantity = quantity, price = price)
                stockViewModel.addStock(newStock)
                editStockName.text.clear()
                editStockQuantity.text.clear()
                editStockPrice.text.clear()
            }
        }
    }
}
