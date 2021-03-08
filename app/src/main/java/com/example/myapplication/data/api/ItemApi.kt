package com.example.myapplication.api

import com.example.myapplication.data.model.Item
import com.example.myapplication.data.model.ItemRequest
import retrofit2.Response
import retrofit2.http.*

interface ItemApi {

    @GET("item")
    suspend fun getData(): Response<List<Item>>

    @POST("item")
    suspend fun addData(@Body request : ItemRequest): Response<Item>

    @DELETE("item/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Item>

    @GET("item/{id}")
    suspend fun findById(@Path("id") id: Int): Response<Item>

    @PUT("item/{id}")
    suspend fun editById(@Path("id") id: Int, @Body request: ItemRequest): Response<Item>
}