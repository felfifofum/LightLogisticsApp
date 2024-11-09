package com.example.lightlogisticsapp.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lightlogisticsapp.R
import com.example.lightlogisticsapp.model.Order

class OrderAdapter : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderId: TextView = itemView.findViewById(R.id.order_id)
        private val orderName: TextView = itemView.findViewById(R.id.order_name)
        private val orderQuantity: TextView = itemView.findViewById(R.id.order_quantity)
        private val orderPrice: TextView = itemView.findViewById(R.id.order_price)
        private val customerName: TextView = itemView.findViewById(R.id.customer_name)
        private val deliveryDate: TextView = itemView.findViewById(R.id.delivery_date)

        fun bind(order: Order) {
            orderId.text = "ID: ${order.id}"
            orderName.text = "Name: ${order.name}"
            orderQuantity.text = "Quantity: ${order.quantity}"
            orderPrice.text = "Price: ${order.price}"
            customerName.text = "Customer: ${order.customerName}"
            deliveryDate.text = "Delivery Date: ${order.deliveryDate}"
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}
