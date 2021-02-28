package com.example.myapplication.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.presentation.data.model.Item
import com.example.myapplication.presentation.data.repository.ItemRepositoryInterface
import com.example.myapplication.presentation.listener.ItemClickListener

class ListViewModel(private val repository: ItemRepositoryInterface): ViewModel(), ItemClickListener {

    private var _itemsLiveData = MutableLiveData<List<Item>>()

    val itemsLiveData: LiveData<List<Item>>
        get() {
            loadItemData()
            return _itemsLiveData
        }

    private fun loadItemData() {
        _itemsLiveData.value = repository.list()
    }

    override fun onDelete(item: Item) {
        repository.delete(item)
        loadItemData()
    }

}