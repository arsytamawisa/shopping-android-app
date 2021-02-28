package com.example.myapplication.presentation.data.repository

import com.example.myapplication.presentation.data.model.Item

interface ItemRepositoryInterface {
    fun list() : List<Item>
    fun save(item:Item) : Item
    fun delete(item:Item) : Item
}