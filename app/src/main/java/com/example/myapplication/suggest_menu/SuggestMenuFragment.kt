package com.example.myapplication.suggest_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.add_menu.NewMenuAdapter
import com.example.myapplication.database.DinnerDatabase
import com.example.myapplication.databinding.FragmentSuggestMenuBinding

class SuggestMenuFragment : Fragment() {

    private lateinit var viewModel: SuggestMenuViewModel
    private lateinit var viewModelFactory: SuggestMenuViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSuggestMenuBinding>(inflater,
            R.layout.fragment_suggest_menu, container, false)

        val args = SuggestMenuFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(this.activity).application

        val dataSource = DinnerDatabase.getInstance(application).dinnerMenuDao

        viewModelFactory = SuggestMenuViewModelFactory(args.dinnerType, dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SuggestMenuViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.suggestMenuViewModel = viewModel

        val adapter = NewMenuAdapter()
        binding.suggestedMenuList.adapter = adapter
        viewModel.suggestedMenu.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.suggestNewMenuButton.setOnClickListener {
            viewModel.setNewMenu()
        }

        Toast.makeText(context, "The Selected Dinner Type: ${args.dinnerType.uppercase()}", Toast.LENGTH_SHORT).show()

        return binding.root
    }

}