package com.example.lightlogisticsapp.ui.order

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightlogisticsapp.R
import com.example.lightlogisticsapp.model.Order
import com.example.lightlogisticsapp.ui.shared.SharedViewModel

class OrderFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderAdapter = OrderAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = orderAdapter

        // Observing the orders LiveData from SharedViewModel
        sharedViewModel.orders.observe(viewLifecycleOwner) { orders ->
            // Submit a fresh list
            orderAdapter.submitList(orders.toList())
        }

        val addOrderButton = view.findViewById<Button>(R.id.add_order_button)
        val clearOrderButton = view.findViewById<Button>(R.id.clear_order_button)
        val pickDeliveryDateButton = view.findViewById<Button>(R.id.pick_delivery_date)
        val selectedDeliveryDateText = view.findViewById<TextView>(R.id.selected_delivery_date)

        val editOrderName = view.findViewById<EditText>(R.id.edit_order_name)
        val editOrderQuantity = view.findViewById<EditText>(R.id.edit_order_quantity)
        val editOrderPrice = view.findViewById<EditText>(R.id.edit_order_price)
        val editCustomerName = view.findViewById<EditText>(R.id.edit_customer_name)

        var deliveryDate: String = ""
        pickDeliveryDateButton.setOnClickListener {
            val datePicker = DatePickerDialog(requireContext(), { _, year, month, day ->
                deliveryDate = "$day/${month + 1}/$year"
                selectedDeliveryDateText.text = "Selected Date: $deliveryDate"
            }, 2024, 0, 1)
            datePicker.show()
        }

        addOrderButton.setOnClickListener {
            val name = editOrderName.text.toString().trim()
            val quantity = editOrderQuantity.text.toString().toIntOrNull() ?: 0
            val price = editOrderPrice.text.toString().toDoubleOrNull() ?: 0.0
            val customerName = editCustomerName.text.toString().trim()

            if (name.isNotEmpty() && customerName.isNotEmpty() && deliveryDate.isNotEmpty()) {
                val newOrder = Order(
                    id = System.currentTimeMillis().toString(),
                    name = name,
                    quantity = quantity,
                    price = price,
                    customerName = customerName,
                    deliveryDate = deliveryDate
                )
                val orderPlaced = sharedViewModel.addOrder(newOrder)
                if (orderPlaced) {
                    Toast.makeText(context, "Order placed successfully", Toast.LENGTH_SHORT).show()
                    clearInputs(editOrderName, editOrderQuantity, editOrderPrice, editCustomerName, selectedDeliveryDateText)
                } else {
                    Toast.makeText(context, "Not enough stock available or invalid stock", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }

        clearOrderButton.setOnClickListener {
            clearInputs(editOrderName, editOrderQuantity, editOrderPrice, editCustomerName, selectedDeliveryDateText)
        }
    }

    private fun clearInputs(
        name: EditText, quantity: EditText, price: EditText,
        customer: EditText, deliveryDate: TextView
    ) {
        name.text.clear()
        quantity.text.clear()
        price.text.clear()
        customer.text.clear()
        deliveryDate.text = "Selected Date: None"
    }
}