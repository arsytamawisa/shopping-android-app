package com.example.myapplication.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Item
import com.example.myapplication.databinding.CardViewItemlistBinding
import com.example.myapplication.presentation.listener.ItemClickListener

class ListViewHolder(view: View, val itemClickListener: ItemClickListener) :
    RecyclerView.ViewHolder(view) {
    private val binding = CardViewItemlistBinding.bind(view)

    fun bind(item: Item) {
        binding.apply {
            nameTv.text = "Item name: ${item.name}"
            quantityTv.text = "Quantity: ${item.quantity.toString()}"
            dateTv.text = "Date: ${item.date}"
            noteTv.text = "Note: ${item.note}"
            deleteBtn.setOnClickListener {
                itemClickListener.onDelete(item.id)
            }
            cardItem.setOnClickListener {
                itemClickListener.onEdit(item.id)
            }
        }
    }
}