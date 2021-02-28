package com.example.latihanframgent.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.data.model.Item
import com.example.myapplication.presentation.list.ListViewHolder
import com.example.myapplication.presentation.listener.ItemClickListener

class ListViewAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ListViewHolder>() {
    var items = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.card_view_itemlist, parent, false)
        return ListViewHolder(itemView, itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun setData(data: List<Item>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}
