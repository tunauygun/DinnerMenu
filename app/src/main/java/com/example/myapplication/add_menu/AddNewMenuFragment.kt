package com.example.myapplication.add_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.database.DinnerDatabase
import com.example.myapplication.databinding.FragmentAddNewMenuBinding
import com.google.android.material.snackbar.Snackbar

class AddNewMenuFragment : Fragment() {

    private lateinit var viewModel: AddNewMenuViewModel
    private lateinit var viewModelFactory: AddNewMenuViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddNewMenuBinding>(inflater,
            R.layout.fragment_add_new_menu, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DinnerDatabase.getInstance(application).dinnerMenuDao

        viewModelFactory = AddNewMenuViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddNewMenuViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.addNewMenuViewModel = viewModel

        val adapter = NewMenuAdapter()
        binding.newMenuList.adapter = adapter
        viewModel.newMenuItems.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        viewModel.newMenuLength.observe(viewLifecycleOwner, Observer {
            if(it > 0){
                binding.NewMenuTitle.visibility = View.VISIBLE
            } else {
                binding.NewMenuTitle.visibility = View.INVISIBLE
            }
        })


        binding.newMenuTextInputLayout.setEndIconOnClickListener {
            val newItem = binding.newMenuEditText.text.toString()
            binding.newMenuEditText.text?.clear()
            viewModel.addItemToNewMenuItems(newItem)
        }

        binding.completeNewMenuButton.setOnClickListener {
            viewModel.addNewMenuToDatabase()
        }

        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            var selectedRadioButton: String = "notSelected"
            when(i){
                R.id.teaRadioButton -> selectedRadioButton = "tea"
                R.id.normalRadioButton -> selectedRadioButton = "normal"
            }
            viewModel.selectedMenuType = selectedRadioButton
        })

        viewModel.snackbarToDisplay.observe(viewLifecycleOwner, Observer {
            Snackbar.make(binding.divider, it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.toastToDisplay.observe(viewLifecycleOwner, Observer {
            Toast.makeText(application.applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.clearRadioButtons.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.radioGroup.clearCheck()
                viewModel.clearRadioButtons.value = false
            }
        })

        //DEBUG
        binding.debugDatabaseClearButton.setOnClickListener {
            //viewModel.onClear()
            //Toast.makeText(context, "Database is cleared!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }






}