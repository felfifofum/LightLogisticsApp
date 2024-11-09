package com.example.lightlogisticsapp.ui.stock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lightlogisticsapp.R
import com.example.lightlogisticsapp.model.AbstractStock

class StockAdapter(private val onUpdateQuantity: (AbstractStock<*>, Int) -> Unit) : ListAdapter<AbstractStock<*>, StockAdapter.StockViewHolder>(StockDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock, onUpdateQuantity)
    }

    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stockName: TextView = itemView.findViewById(R.id.stock_name)
        private val stockQuantity: TextView = itemView.findViewById(R.id.stock_quantity)
        private val editQuantity: EditText = itemView.findViewById(R.id.edit_quantity)
        private val updateButton: Button = itemView.findViewById(R.id.update_button)

        fun bind(stock: AbstractStock<*>, onUpdateQuantity: (AbstractStock<*>, Int) -> Unit) {
            stockName.text = stock.name
            stockQuantity.text = "Quantity: ${stock.quantity}"

            updateButton.setOnClickListener {
                val newQuantity = editQuantity.text.toString().toIntOrNull()
                if (newQuantity != null) {
                    onUpdateQuantity(stock, newQuantity)

                    // Hide  keyboard
                    val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(itemView.windowToken, 0)
                }
            }
        }
    }

    class StockDiffCallback : DiffUtil.ItemCallback<AbstractStock<*>>() {
        override fun areItemsTheSame(oldItem: AbstractStock<*>, newItem: AbstractStock<*>): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AbstractStock<*>, newItem: AbstractStock<*>): Boolean {
            return oldItem == newItem
        }
    }
}
