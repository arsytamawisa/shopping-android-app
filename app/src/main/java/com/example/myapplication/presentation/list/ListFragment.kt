package com.example.myapplication.presentation.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanframgent.list.ListViewAdapter
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.data.repository.ItemRepository


class ListFragment : Fragment() {

    lateinit var binding : FragmentListBinding
    lateinit var viewModel : ListViewModel
    lateinit var rvAdapter : ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        binding.apply {
            rvAdapter = ListViewAdapter(viewModel)
            itemRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = rvAdapter
            }
        }
        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = ItemRepository()
                return ListViewModel(repository) as T
            }
        }).get(ListViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.liveDataItems.observe(this) {
            rvAdapter.setData(it)
        }

        viewModel.liveDataItem.observe(this) {
            Log.d("GET ITEM", "$it")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}