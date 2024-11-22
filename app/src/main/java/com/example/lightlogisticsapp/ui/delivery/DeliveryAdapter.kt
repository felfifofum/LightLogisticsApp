package com.example.lightlogisticsapp.ui.delivery

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lightlogisticsapp.R
import com.example.lightlogisticsapp.model.Delivery
import com.example.lightlogisticsapp.ui.shared.SharedViewModel

/**
 * DeliveryAdapter is a custom RecyclerView adapter that binds a list of Delivery objects
 * to display their information in a RecyclerView.
 *
 * @param sharedViewModel The SharedViewModel that provides the data for deliveries.
 */
class DeliveryAdapter(private val sharedViewModel: SharedViewModel) :
    ListAdapter<Delivery, DeliveryAdapter.DeliveryViewHolder>(DeliveryDiffCallback()) {

    private var selectedDeliveryId: String? = null // The ID of the currently selected delivery
    private var selectedPosition: Int = RecyclerView.NO_POSITION // The position of the selected item

    /**
     * Called to create a new ViewHolder instance when a new item is created.
     *
     * @param parent The ViewGroup that will contain the new view.
     * @param viewType The type of the view (not used in this case).
     * @return A new instance of DeliveryViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        // Inflate the layout for each delivery item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delivery, parent, false)
        return DeliveryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val delivery = getItem(position)
        holder.bind(delivery)

        // Change background color of the selected item to indicate selection
        holder.itemView.setBackgroundColor(
            if (position == selectedPosition) {
                Color.parseColor("#D3D3D3") // Light gray for selected item
            } else {
                Color.WHITE // Default background for unselected items
            }
        )
    }

    fun getSelectedDeliveryId(): String? = selectedDeliveryId

    inner class DeliveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val destinationText: TextView = itemView.findViewById(R.id.delivery_destination)
        private val statusText: TextView = itemView.findViewById(R.id.delivery_status)
        private val itemsText: TextView = itemView.findViewById(R.id.delivery_items)
        private val deliveryIdText: TextView = itemView.findViewById(R.id.delivery_id)

        fun bind(delivery: Delivery) {
            destinationText.text = "Destination: ${delivery.destination}"
            statusText.text = "Status: ${delivery.status}"

            // Display the items in the delivery
            if (delivery.items.isNotEmpty()) {
                // Show items in a comma-separated format or something more readable
                val itemNames = delivery.items.joinToString { it.name }
                itemsText.text = "Items: $itemNames"
            } else {
                itemsText.text = "No items"
            }

            deliveryIdText.text = "Delivery ID: ${delivery.id}"

            itemView.setOnClickListener {
                selectedPosition = adapterPosition
                selectedDeliveryId = delivery.id
                notifyDataSetChanged()
            }
        }
    }
}

class DeliveryDiffCallback : DiffUtil.ItemCallback<Delivery>() {
    override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
        oldItem == newItem
}