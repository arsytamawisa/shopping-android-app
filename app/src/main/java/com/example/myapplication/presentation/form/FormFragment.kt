package com.example.myapplication.presentation.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFormBinding
import com.example.myapplication.presentation.data.model.Item
import com.example.myapplication.presentation.data.repository.ItemRepository
import com.example.myapplication.presentation.list.ListViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var viewModel: FormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(layoutInflater)

        binding.apply {

            dateTiet.inputType = InputType.TYPE_NULL
            dateTiet.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
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
            }


            submitBtn.setOnClickListener {
                // dateEt.setDate()

                var quantity: Int = if (quantityEt.editText?.text.toString().isNullOrBlank()) {
                    0
                } else {
                    quantityEt.editText?.text.toString().toInt()
                }

                val item = Item(
                    name = nameEt.editText?.text.toString(),
                    note = noteEt.editText?.text.toString(),
                    quantity = quantity,
                    date = dateEt.editText?.text.toString(),
                    id = ""
                )

                viewModel.save(item)

                pushNotification(true)
            }

            cancelBtn.setOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
        }

        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = ItemRepository()
                return FormViewModel(repository) as T
            }
        }).get(FormViewModel::class.java)
    }

    private fun subscribe() {
        viewModel._itemLiveData.observe(this) {
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FormFragment()
    }


    private fun pushNotification(status: Boolean) {
        if (status) {
            Toast.makeText(
                activity,
                "Success add new item.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                activity,
                "Oops.. sorry please check your input form",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun EditText.setDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        this.setInputType(InputType.TYPE_NULL)
        this.setOnClickListener(View.OnClickListener {
            val datePickerDialog = activity?.let { it1 ->
                DatePickerDialog(
                    it1, DatePickerDialog.OnDateSetListener
                    { view, year, monthOfYear, dayOfMonth ->
                        this.setText(
                            "$year/$monthOfYear/$dayOfMonth",
                            TextView.BufferType.EDITABLE
                        );
                    }, year, month, day
                )
            }
            datePickerDialog?.show()
        })
    }
}
