package com.example.myapplication.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardViewItemlistBinding
import com.example.myapplication.presentation.data.model.Item
import com.example.myapplication.presentation.listener.ItemClickListener

class ListViewHolder(view: View, private val itemClickListener: ItemClickListener) :
    RecyclerView.ViewHolder(view) {
    private val binding = CardViewItemlistBinding.bind(view)

    fun bind(item: Item) {
        binding.apply {
            /* Set Data */
            nameTv.text     = item.name
            dateTv.text     = item.date
            noteTv.text     = item.note
            quantityTv.text = item.quantity.toString()

            /* Delete Listener */
            deleteBtn.setOnClickListener {
                itemClickListener.onDelete(item)
            }
        }
    }
}