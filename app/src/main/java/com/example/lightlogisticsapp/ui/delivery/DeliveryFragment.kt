package com.example.lightlogisticsapp.ui.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lightlogisticsapp.databinding.FragmentDeliveryBinding

class DeliveryFragment : Fragment() {

    private var _binding: FragmentDeliveryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orderViewModel =
            ViewModelProvider(this).get(DeliveryViewModel::class.java)

        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDelivery
        orderViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}