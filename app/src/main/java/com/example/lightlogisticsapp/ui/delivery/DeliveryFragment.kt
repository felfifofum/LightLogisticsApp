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

        deliveryAdapter = DeliveryAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = deliveryAdapter

        sharedViewModel.deliveries.observe(viewLifecycleOwner, Observer { deliveries ->
            deliveryAdapter.submitList(deliveries)
        })

        val addDeliveryButton = view.findViewById<Button>(R.id.add_delivery_button)
        val editDeliveryDestination = view.findViewById<EditText>(R.id.edit_delivery_destination)
        val editDeliveryItem = view.findViewById<EditText>(R.id.edit_delivery_item)
        val updateStatusButton = view.findViewById<Button>(R.id.update_status_button)

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

        updateStatusButton.setOnClickListener {
            val deliveryId = "1" // Replace with the actual delivery ID you want to update
            sharedViewModel.updateDeliveryStatus(deliveryId, DeliveryStatus.IN_TRANSIT)
            Toast.makeText(context, "Delivery status updated to IN_TRANSIT", Toast.LENGTH_SHORT).show()
        }
    }
}
