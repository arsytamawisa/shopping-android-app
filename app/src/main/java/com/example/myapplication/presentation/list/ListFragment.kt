package com.example.myapplication.presentation.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.repository.ItemRepository
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.form.FormViewModel
import com.example.myapplication.list.ListViewAdapter
import com.example.myapplication.list.ListViewModel
import com.example.myapplication.utils.ResourceStatus

class ListFragment : Fragment() {

    lateinit var viewModel: ListViewModel
    lateinit var viewModelForm: FormViewModel
    lateinit var binding: FragmentListBinding
    lateinit var rvAdapter: ListViewAdapter
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView", "irfaaaaaaaaaan")
        binding = FragmentListBinding.inflate(layoutInflater)
        viewModel.getAllData()
        binding.apply {

            rvAdapter = ListViewAdapter(viewModel)
            recyclerViewItem.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = rvAdapter
            }

            addItemFab.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_formFragment)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ONVIEWCREATE", "atmim")
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepository()
                return ListViewModel(repo) as T
            }
        }).get(ListViewModel::class.java)
    }

    private fun subscribe() {
        //show list item
        viewModel.itemsLiveData.observe(this) {
            Log.d("SUBSCRIBE", "ekaaa")
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("APP", "Loading..")
                ResourceStatus.SUCCESS -> {
                    val data : List<Item> = it.data as List<Item>
                    rvAdapter.setData(data)
                }
            }
        }

        viewModel.updateLiveData.observe(this) {
            val response = it.data as Item
            Log.d("GET ITEM", "${response}")
            Navigation.findNavController(requireView())
                .navigate(
                    R.id.action_listFragment_to_formFragment,
                    bundleOf("edit_item" to response)
                )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}