package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentTypeChooserBinding

class TypeChooserFragment : Fragment() {

    private lateinit var binding: FragmentTypeChooserBinding
    private lateinit var dinner_type: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentTypeChooserBinding>(inflater,
            R.layout.fragment_type_chooser, container, false)

        binding.normalTypeButton.setOnClickListener{
            dinner_type = "normal"
            navigateToSuggestionFragment()
        }

        binding.teaTypeButton.setOnClickListener{
            dinner_type = "tea"
            navigateToSuggestionFragment()
        }

        binding.randomTypeButton.setOnClickListener{
            dinner_type = "random"
            navigateToSuggestionFragment()
        }

        return binding.root
    }

    private fun navigateToSuggestionFragment(){
        binding.normalTypeButton.findNavController().navigate(
            TypeChooserFragmentDirections.actionTypeChooserFragmentToSuggestMenuFragment(
                dinner_type
            )
        )

    }

}