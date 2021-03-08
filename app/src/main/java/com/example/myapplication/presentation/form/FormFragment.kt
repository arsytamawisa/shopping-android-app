package com.example.myapplication.presentation.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.model.ItemRequest
import com.example.myapplication.data.repository.ItemRepository
import com.example.myapplication.databinding.FragmentFormBinding
import com.example.myapplication.form.FormViewModel
import com.example.myapplication.utils.ResourceStatus
import com.example.shoppinglist.utils.components.LoadingDialog
import java.util.*

class FormFragment : Fragment() {

    private var itemValue: Item? = null
    private lateinit var binding: FragmentFormBinding
    private lateinit var viewModel: FormViewModel
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemValue = it.getParcelable<Item>("edit_item")
        }
        initModel()
        subscribe()
        Log.d("ITEM", "${itemValue}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        loadingDialog = LoadingDialog.build(requireContext())
        binding = FragmentFormBinding.inflate(layoutInflater)
        binding.apply {

            itemValue?.apply {
                submitBtn.text = "UPDATE"
                titleItem.text = "EDIT ITEM"

                dateTiet.setText(date)
                nameEt.editText?.setText(name)
                quantityEt.editText?.setText(quantity.toString())
                noteEt.editText?.setText(note)
            }

            dateTiet.inputType = InputType.TYPE_NULL
            dateTiet.setOnClickListener(View.OnClickListener {
                val datePickerDialog = activity?.let { it1 ->
                    DatePickerDialog(
                        it1, DatePickerDialog.OnDateSetListener
                        { view, year, monthOfYear, dayOfMonth ->
                            val date = "$dayOfMonth/$monthOfYear/$year"
                            dateTiet.setText(date)
                        }, year, month, day
                    )
                }
                datePickerDialog?.show()
            })

            submitBtn.setOnClickListener{
                var quantity: Int = if (quantityEt.editText?.text.toString().isNullOrEmpty()) {
                    0
                } else {
                    quantityEt.editText?.text.toString().toInt()
                }

                if (itemValue == null) {
                    //add
                    Log.d("data", "jalan")
                    itemValue = Item(
                        note = noteEt.editText?.text.toString(),
                        name = nameEt.editText?.text.toString(),
                        date = dateEt.editText?.text.toString(),
                        quantity = quantity,
                        id = 0
                    )
                } else {
                    //update
                    itemValue?.id?.let { it ->
                        Log.d("btn EDIT", "$it")
                        itemValue = Item(
                            note = noteEt.editText?.text.toString(),
                            name = nameEt.editText?.text.toString(),
                            date = dateEt.editText?.text.toString(),
                            quantity = quantity,
                            id = it
                        )
                        Log.d("btn EDIT", "$itemValue")
                    }
                }
                viewModel.validation(itemValue!!)
            }

            cancelBtn.setOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
        }
        return binding.root
    }

    private fun initModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo =
                    ItemRepository()
                return FormViewModel(repo) as T
            }
        }).get(FormViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.itemLiveData.observe(this) {
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }

        viewModel.isValid.observe(this) {
            when(it.status) {
                ResourceStatus.FAIL -> {
                    loadingDialog.hide()
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    val request = itemValue?.id?.let { it1 ->
                        ItemRequest(
                            name = binding.nameEt.editText?.text.toString(),
                            date = binding.dateTiet.text.toString(),
                            note = binding.noteEt.editText?.text.toString(),
                            quantity = binding.quantityEt.editText?.text.toString().toInt(),
                            id = it1
                        )
                    }

                    if (request != null) {
                        viewModel.addData(request)
                    }
                    loadingDialog.hide()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FormFragment()
    }
}