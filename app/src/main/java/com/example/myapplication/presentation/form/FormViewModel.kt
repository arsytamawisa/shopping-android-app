package com.example.myapplication.presentation.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.repository.ItemRepository

class FormViewModel(val repository: ItemRepository) : ViewModel() {

    var _itemLiveData = MutableLiveData<Item>()

    val itemLiveData : LiveData<Item>
        get() {
            return _itemLiveData
        }

    fun save(item: Item) {
        _itemLiveData.value = repository.save(item)
    }
}