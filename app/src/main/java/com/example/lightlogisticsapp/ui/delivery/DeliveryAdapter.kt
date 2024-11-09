package com.example.lightlogisticsapp.ui.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lightlogisticsapp.R
import com.example.lightlogisticsapp.model.Delivery

class DeliveryAdapter : ListAdapter<Delivery, DeliveryAdapter.DeliveryViewHolder>(DeliveryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_delivery, parent, false)
        return DeliveryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val delivery = getItem(position)
        holder.bind(delivery)
    }

    class DeliveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deliveryId: TextView = itemView.findViewById(R.id.delivery_id)
        private val deliveryDestination: TextView = itemView.findViewById(R.id.delivery_destination)
        private val deliveryStatus: TextView = itemView.findViewById(R.id.delivery_status)
        private val deliveryItems: TextView = itemView.findViewById(R.id.delivery_items)

        fun bind(delivery: Delivery) {
            deliveryId.text = "ID: ${delivery.id}"
            deliveryDestination.text = "Destination: ${delivery.destination}"
            deliveryStatus.text = "Status: ${delivery.status}"
            deliveryItems.text = "Items: ${delivery.items.joinToString { it.name }}"
        }
    }

    class DeliveryDiffCallback : DiffUtil.ItemCallback<Delivery>() {
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem == newItem
        }
    }
}
