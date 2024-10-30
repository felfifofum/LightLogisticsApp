package com.example.lightlogisticsapp.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lightlogisticsapp.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orderViewModel =
            ViewModelProvider(this).get(OrderViewModel::class.java)

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textOrder
        val orderDetailsTextView: TextView = binding.textOrderDetails
        val orderDetailsEditText: EditText = binding.editTextOrderDetails
        val updateOrderButton: Button = binding.buttonUpdateOrder

        orderViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        orderViewModel.orderDetails.observe(viewLifecycleOwner) { details ->
            orderDetailsTextView.text = details
        }

        updateOrderButton.setOnClickListener {
            val details = orderDetailsEditText.text.toString()
            orderViewModel.updateOrderDetails(details)
            orderDetailsEditText.text.clear()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
