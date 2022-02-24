package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title, container, false)

        binding.suggestMenuButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_typeChooserFragment)
        )

        binding.addNewMenuButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_addNewMenuFragment)
        )

        return binding.root
    }

}



