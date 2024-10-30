package com.example.lightlogisticsapp.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lightlogisticsapp.databinding.FragmentStockBinding
import com.example.lightlogisticsapp.model.Stock

class StockFragment : Fragment() {

    private var _binding: FragmentStockBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val stockViewModel =
            ViewModelProvider(this).get(StockViewModel::class.java)

        _binding = FragmentStockBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textStock
        val eventTextView: TextView = binding.textEvent
        val stockIdEditText: EditText = binding.editTextStockId
        val stockNameEditText: EditText = binding.editTextStockName
        val quantityEditText: EditText = binding.editTextQuantity
        val priceEditText: EditText = binding.editTextPrice
        val addStockButton: Button = binding.buttonAddStock
        val updateQuantityButton: Button = binding.buttonUpdateQuantity
        val updatePriceButton: Button = binding.buttonUpdatePrice
        val placeOrderButton: Button = binding.buttonPlaceOrder

        stockViewModel.stock.observe(viewLifecycleOwner) { stock ->
            textView.text = "Item: ${stock.name}, Quantity: ${stock.quantity}, Price: ${stock.price}"
        }

        stockViewModel.events.observe(viewLifecycleOwner) { event ->
            eventTextView.text = event
        }

        addStockButton.setOnClickListener {
            val stockId = stockIdEditText.text.toString()
            val stockName = stockNameEditText.text.toString()
            val quantity = quantityEditText.text.toString().toIntOrNull()
            val price = priceEditText.text.toString().toDoubleOrNull()
            if (stockId.isNotEmpty() && stockName.isNotEmpty() && quantity != null && price != null) {
                stockViewModel.addNewStock(Stock(stockId, stockName, quantity, price))
            }
        }

        updateQuantityButton.setOnClickListener {
            val newQuantity = quantityEditText.text.toString().toIntOrNull()
            newQuantity?.let {
                stockViewModel.updateStockQuantity(it)
            }
        }

        updatePriceButton.setOnClickListener {
            val newPrice = priceEditText.text.toString().toDoubleOrNull()
            newPrice?.let {
                stockViewModel.updateStockPrice(it)
            }
        }

        placeOrderButton.setOnClickListener {
            val orderQuantity = quantityEditText.text.toString().toIntOrNull()
            orderQuantity?.let {
                stockViewModel.placeOrder(it)
            }
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
