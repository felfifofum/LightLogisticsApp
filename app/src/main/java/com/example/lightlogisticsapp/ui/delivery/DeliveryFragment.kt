package com.example.lightlogisticsapp.ui.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lightlogisticsapp.databinding.FragmentDeliveryBinding
import com.example.lightlogisticsapp.model.Stock

class DeliveryFragment : Fragment() {

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val deliveryViewModel =
            ViewModelProvider(this).get(DeliveryViewModel::class.java)

        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDelivery
        val eventTextView: TextView = binding.textEvent
        val statusEditText: EditText = binding.editTextStatus
        val itemIdEditText: EditText = binding.editTextItemId
        val itemNameEditText: EditText = binding.editTextItemName
        val itemQuantityEditText: EditText = binding.editTextItemQuantity
        val itemPriceEditText: EditText = binding.editTextItemPrice
        val updateStatusButton: Button = binding.buttonUpdateStatus
        val addItemButton: Button = binding.buttonAddItem

        deliveryViewModel.delivery.observe(viewLifecycleOwner) { delivery ->
            textView.text = "Delivery to: ${delivery.destination}, Status: ${delivery.status}, Items: ${delivery.items.size}"
        }

        deliveryViewModel.events.observe(viewLifecycleOwner) { event ->
            eventTextView.text = event
        }

        updateStatusButton.setOnClickListener {
            val newStatus = statusEditText.text.toString()
            deliveryViewModel.updateDeliveryStatus(newStatus)
            statusEditText.text.clear()
        }

        addItemButton.setOnClickListener {
            val itemId = itemIdEditText.text.toString()
            val itemName = itemNameEditText.text.toString()
            val itemQuantity = itemQuantityEditText.text.toString().toIntOrNull()
            val itemPrice = itemPriceEditText.text.toString().toDoubleOrNull()
            if (itemId.isNotEmpty() && itemName.isNotEmpty() && itemQuantity != null && itemPrice != null) {
                val item = Stock(id = itemId, name = itemName, quantity = itemQuantity, price = itemPrice)
                deliveryViewModel.addDeliveryItem(item)
                clearFields()
            }
        }

        return root
    }

    private fun clearFields() {
        binding.editTextItemId.text.clear()
        binding.editTextItemName.text.clear()
        binding.editTextItemQuantity.text.clear()
        binding.editTextItemPrice.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
