package com.example.myapplication.suggest_menu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.DinnerMenuDao

class SuggestMenuViewModelFactory(private val dinner_type: String, private val dataSource: DinnerMenuDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuggestMenuViewModel::class.java)) {
            return SuggestMenuViewModel(dinner_type, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}