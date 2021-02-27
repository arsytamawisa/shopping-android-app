package com.example.myapplication.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardViewItemlistBinding
import com.example.myapplication.presentation.data.model.Item

class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = CardViewItemlistBinding.bind(view)

    fun bind(item: Item) {
        binding.apply {
            nameTv.text = item.name
            quantityTv.text = item.quantity.toString()
            dateTv.text = item.date
            noteTv.text = item.note
        }
    }
}