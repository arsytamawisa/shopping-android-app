package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Item

interface ItemRepositoryInterface {
    fun list() : List<Item>
    fun save(item:Item) : Item
    fun delete(item:Item) : Item
    fun findByItem(item: Item) : Item
}