package com.example.myapplication.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.repository.ItemRepositoryInterface
import com.example.myapplication.presentation.listener.ItemClickListener

class ListViewModel(private val repository: ItemRepositoryInterface): ViewModel(), ItemClickListener {

    private var _itemsLiveData = MutableLiveData<List<Item>>()
    private var _itemLiveData = MutableLiveData<Item>()


    /* Live Data */

    val liveDataItems: LiveData<List<Item>>
        get() {
            loadItems()
            return _itemsLiveData
        }

    val liveDataItem: LiveData<Item>
        get() {
            return _itemLiveData
        }



    /* Load Data */

    private fun loadItems() {
        _itemsLiveData.value = repository.list()
    }

    private fun loadItem(item: Item) {
        _itemLiveData.value = repository.findByItem(item)
    }



    /* Listener */

    override fun onDelete(item: Item) {
        repository.delete(item)
        loadItems()
    }

    override fun onEdit(item: Item) {
        loadItem(item)
    }


}