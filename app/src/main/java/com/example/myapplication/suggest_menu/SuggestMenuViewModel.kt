package com.example.myapplication.suggest_menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.database.DinnerMenu
import com.example.myapplication.database.DinnerMenuDao
import kotlinx.coroutines.*

class SuggestMenuViewModel(dinnerType: String, val database: DinnerMenuDao, application: Application): AndroidViewModel(application){

    private var _suggestedMenu = MutableLiveData<MutableList<String>>()
    val suggestedMenu: LiveData<MutableList<String>>
        get() = _suggestedMenu

    var menuType:String = dinnerType

    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        setNewMenu()
    }

    fun setNewMenu(){
        setNewSuggestedMenu(menuType)
    }

    private fun setNewSuggestedMenu(dinnerType: String){
        uiScope.launch {
            var dinner_type = dinnerType
            if (dinner_type != "tea" && dinner_type != "normal"){
                dinner_type = mutableListOf("tea", "normal").random()
            }
            _suggestedMenu.value = getSuggestedMenu(dinner_type)?.menuList
            Log.i("SuggestMenuViewModel", "New Menu Setting")
        }
    }

    private suspend fun getSuggestedMenu(dinnerType: String): DinnerMenu? {
        return withContext(Dispatchers.IO){
            var suggestedDinnerMenu = database.getRandomMenu(dinnerType)
            Log.i("SuggestMenuViewModel", "New Menu Set")
            suggestedDinnerMenu
        }
    }


    // CLEARING THE DATABASE
    fun onClear(){
        uiScope.launch {
            uiScope.launch {
                clearDatabase()
            }
        }
    }
    suspend fun clearDatabase() {
        withContext(Dispatchers.IO){
            database.clear()

        }
    }


}