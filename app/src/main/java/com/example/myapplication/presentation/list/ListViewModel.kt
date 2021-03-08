package com.example.myapplication.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.ItemRepositoryInterface
import com.example.myapplication.presentation.listener.ItemClickListener
import com.example.myapplication.utils.ResourceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ItemRepositoryInterface) : ViewModel(),
    ItemClickListener {

    private var _itemsLiveData = MutableLiveData<ResourceState>()
    private var _updateLiveData = MutableLiveData<ResourceState>()
    val itemsLiveData: LiveData<ResourceState>
        get() {
            return _itemsLiveData
        }
    val updateLiveData: LiveData<ResourceState>
        get() {
            return _updateLiveData
        }


    fun getAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            _itemsLiveData.postValue(ResourceState.loading())
            val response = repository.getAllItem()
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("DATA", "$it")
                    _itemsLiveData.postValue(ResourceState.success(it))
                }
            } else {
                _itemsLiveData.postValue(ResourceState.fail("error"))
            }
        }
    }

    override fun onDelete(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteItem(id)
            getAllData()
        }
    }

    override fun onEdit(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getItemById(id)
            if(response.isSuccessful) {
                _updateLiveData.postValue(ResourceState.success(response.body()))
            } else {
                _updateLiveData.postValue(ResourceState.fail("error"))
            }
        }
    }
}