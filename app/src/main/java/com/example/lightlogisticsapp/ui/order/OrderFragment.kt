package com.example.lightlogisticsapp.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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

        sharedViewModel.orders.observe(viewLifecycleOwner, Observer { orders ->
            orderAdapter.submitList(orders)
        })

        val addOrderButton = view.findViewById<Button>(R.id.add_order_button)
        val editOrderName = view.findViewById<EditText>(R.id.edit_order_name)
        val editOrderQuantity = view.findViewById<EditText>(R.id.edit_order_quantity)
        val editOrderPrice = view.findViewById<EditText>(R.id.edit_order_price)
        val editCustomerName = view.findViewById<EditText>(R.id.edit_customer_name)
        val editDeliveryDate = view.findViewById<EditText>(R.id.edit_delivery_date)

        addOrderButton.setOnClickListener {
            val name = editOrderName.text.toString()
            val quantity = editOrderQuantity.text.toString().toIntOrNull() ?: 0
            val price = editOrderPrice.text.toString().toDoubleOrNull() ?: 0.0
            val customerName = editCustomerName.text.toString()
            val deliveryDate = editDeliveryDate.text.toString()

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
                    editOrderName.text.clear()
                    editOrderQuantity.text.clear()
                    editOrderPrice.text.clear()
                    editCustomerName.text.clear()
                    editDeliveryDate.text.clear()
                } else {
                    Toast.makeText(context, "Not enough stock available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
