package com.example.myapplication.presentation.data.repository

import com.example.myapplication.presentation.data.model.Item

interface ItemRepositoryInterface {
    fun list() : List<Item>
}