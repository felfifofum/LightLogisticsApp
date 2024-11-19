package com.example.lightlogisticsapp.ui.stock

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightlogisticsapp.R
import com.example.lightlogisticsapp.model.PerishableStock
import com.example.lightlogisticsapp.model.Stock
import com.example.lightlogisticsapp.ui.shared.SharedViewModel

class StockFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var stockAdapter: StockAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stockNameEditText: EditText = view.findViewById(R.id.edit_stock_name)
        val stockQuantityEditText: EditText = view.findViewById(R.id.edit_stock_quantity)
        val stockPriceEditText: EditText = view.findViewById(R.id.edit_stock_price)
        val perishableCheckBox: CheckBox = view.findViewById(R.id.checkbox_perishable)
        val expirationDateEditText: EditText = view.findViewById(R.id.edit_expiration_date)
        val addButton: Button = view.findViewById(R.id.add_stock_button)

        perishableCheckBox.setOnCheckedChangeListener { _, isChecked ->
            expirationDateEditText.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        stockAdapter = StockAdapter { stock, newQuantity ->
            sharedViewModel.updateStockQuantity(stock.id, newQuantity)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = stockAdapter

        sharedViewModel.stocks.observe(viewLifecycleOwner, Observer { stocks ->
            stockAdapter.submitList(stocks)
        })

        addButton.setOnClickListener {
            val stockName = stockNameEditText.text.toString()
            val stockQuantity = stockQuantityEditText.text.toString().toIntOrNull() ?: 0
            val stockPrice = stockPriceEditText.text.toString().toDoubleOrNull() ?: 0.0

            if (perishableCheckBox.isChecked) {
                val expirationDate = expirationDateEditText.text.toString()
                val newStock = PerishableStock("3", stockName, stockQuantity, stockPrice, expirationDate)
                sharedViewModel.addStock(newStock)
            } else {
                val newStock = Stock("3", stockName, stockQuantity, stockPrice)
                sharedViewModel.addStock(newStock)
            }

            // Clear text boxes
            stockNameEditText.text.clear()
            stockQuantityEditText.text.clear()
            stockPriceEditText.text.clear()
            expirationDateEditText.text.clear()
            perishableCheckBox.isChecked = false

            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}