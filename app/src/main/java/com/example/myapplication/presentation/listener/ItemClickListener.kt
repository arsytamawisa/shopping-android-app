package com.example.myapplication.presentation.listener

import com.example.myapplication.data.model.Item

interface ItemClickListener {

    fun onDelete(item: Item)
    fun onEdit(item: Item)
}