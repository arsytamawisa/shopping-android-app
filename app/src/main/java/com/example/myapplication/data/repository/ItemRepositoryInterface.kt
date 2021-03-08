package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Item
import com.example.myapplication.data.model.ItemRequest
import retrofit2.Response

interface ItemRepositoryInterface {
    suspend fun getAllItem(): Response<List<Item>>
    suspend fun getItemById(id: Int): Response<Item>
    suspend fun addItem(request: ItemRequest): Response<Item>
    suspend fun deleteItem(id: Int): Response<Item>
    suspend fun editItem(request: ItemRequest): Response<Item>
}