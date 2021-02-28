package com.example.myapplication.presentation.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Message
import android.text.InputType
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
            dateTiet.setDate()
            submitBtn.setOnClickListener {
                if (filterBlank(nameEt, noteEt, dateEt, quantityEt)) {
                    val item = Item(
                        name = nameEt.editText?.text.toString(),
                        note = noteEt.editText?.text.toString(),
                        quantity = quantityEt.editText?.text.toString().toInt(),
                        date = dateEt.editText?.text.toString(),
                        id = ""
                    )
                    viewModel.save(item).successNotification()
                } else {
                    failedNotification()
                }

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

    private fun filterBlank(vararg values: TextInputLayout): Boolean {
        var status = true
        values.forEach { if (it.editText?.text.toString().trim().length == 0) status = false }
        return status
    }

    private fun <T> T.successNotification(customMessage: String? = null) {
        var message = customMessage ?: "Success add new item."
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun <T> T.failedNotification(customMessage: String? = null) {
        var message = customMessage ?: "Oops.. sorry please check your input form"
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun TextInputLayout.onlyNumber(): Boolean {
        return "[A-Z0-9<\n]+".toRegex().matches(this.editText?.text.toString())
    }

    private fun TextInputEditText.setDate() {
        this.inputType = InputType.TYPE_NULL
        this.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            this.setOnClickListener {
                val datePickerDialog = activity?.let { it1 ->
                    DatePickerDialog(
                        it1, { view, year, monthOfYear, dayOfMonth ->
                            val date = "$dayOfMonth/$monthOfYear/$year"
                            this.setText(date)
                        }, year, month, day
                    )
                }
                datePickerDialog?.show()
            }
        }
    }
}


