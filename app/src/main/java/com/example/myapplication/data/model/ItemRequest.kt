package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class ItemRequest(

    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("note")
    var note: String? = null,
    @SerializedName("quantity")
    var quantity: Int = 0
)