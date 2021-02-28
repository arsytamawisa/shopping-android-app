package com.example.myapplication.presentation.listener

import com.example.myapplication.presentation.data.model.Item

interface ItemClickListener {

    fun onDelete(item: Item)
}