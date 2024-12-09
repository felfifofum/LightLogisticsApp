package com.example.lightlogisticsapp.ui.delivery

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
import com.example.lightlogisticsapp.model.Delivery
import com.example.lightlogisticsapp.model.DeliveryStatus
import com.example.lightlogisticsapp.model.Stock
import com.example.lightlogisticsapp.ui.shared.SharedViewModel

class DeliveryFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var deliveryAdapter: DeliveryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView and Adapter
        deliveryAdapter = DeliveryAdapter(sharedViewModel)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = deliveryAdapter

        // Observe deliveries and update the list
        sharedViewModel.deliveries.observe(viewLifecycleOwner, Observer { deliveries ->
            deliveryAdapter.submitList(deliveries)
        })

        // Set up Add Delivery Button
        val addDeliveryButton = view.findViewById<Button>(R.id.add_delivery_button)
        val editDeliveryDestination = view.findViewById<EditText>(R.id.edit_delivery_destination)
        val editDeliveryItem = view.findViewById<EditText>(R.id.edit_delivery_item)

        addDeliveryButton.setOnClickListener {
            val destination = editDeliveryDestination.text.toString()
            val itemName = editDeliveryItem.text.toString()
            val stockItem = sharedViewModel.stocks.value?.find { it.name == itemName }

            if (destination.isNotEmpty() && stockItem is Stock) {
                val newDelivery = Delivery(
                    id = System.currentTimeMillis().toString(),
                    destination = destination,
                    status = DeliveryStatus.PENDING,
                    items = listOf(stockItem)
                )
                sharedViewModel.addDelivery(newDelivery)
                Toast.makeText(context, "Delivery added successfully", Toast.LENGTH_SHORT).show()
                editDeliveryDestination.text.clear()
                editDeliveryItem.text.clear()
            } else {
                Toast.makeText(context, "Invalid destination or item", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up Update Status Button
        view.findViewById<Button>(R.id.update_status_button).setOnClickListener {
            val deliveryId = deliveryAdapter.getSelectedDeliveryId()
            if (deliveryId != null) {
                // Find the selected delivery
                val selectedDelivery = sharedViewModel.deliveries.value?.find { it.id == deliveryId }

                selectedDelivery?.let {
                    // Get the current status and find the next status
                    val currentStatus = it.status
                    val nextStatus = when (currentStatus) {
                        DeliveryStatus.PENDING -> DeliveryStatus.IN_TRANSIT
                        DeliveryStatus.IN_TRANSIT -> DeliveryStatus.DELIVERED
                        DeliveryStatus.DELIVERED -> DeliveryStatus.CANCELLED
                        DeliveryStatus.CANCELLED -> DeliveryStatus.PENDING
                    }

                    // Update the delivery status
                    sharedViewModel.updateDeliveryStatus(deliveryId, nextStatus)
                    Toast.makeText(context, "Delivery status updated to $nextStatus", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Select a delivery to update", Toast.LENGTH_SHORT).show()
            }
        }
    }
}