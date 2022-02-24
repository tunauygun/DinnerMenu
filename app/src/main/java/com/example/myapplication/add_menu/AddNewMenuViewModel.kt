package com.example.myapplication.add_menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.database.DinnerMenu
import com.example.myapplication.database.DinnerMenuDao
import kotlinx.coroutines.*

class AddNewMenuViewModel(val database: DinnerMenuDao, application: Application): AndroidViewModel(application){

    private var _newMenuItems = MutableLiveData<MutableList<String>>()
    val newMenuItems: LiveData<MutableList<String>>
        get() = _newMenuItems

    private var _newMenuLength = MutableLiveData<Int>()
    val newMenuLength: LiveData<Int>
        get() = _newMenuLength

    private var _snackbarToDisplay = MutableLiveData<String>()
    val snackbarToDisplay: LiveData<String>
        get() = _snackbarToDisplay

    private var _toastToDisplay = MutableLiveData<String>()
    val toastToDisplay: LiveData<String>
        get() = _toastToDisplay

    var clearRadioButtons = MutableLiveData<Boolean>()

    var selectedMenuType: String = "notSelected"

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        if(_newMenuItems.value == null){
            resetNewMenuItemList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun  createNewMenu(menuType: String, menuList: MutableList<String>){
        uiScope.launch {
            val newDinnerMenu = DinnerMenu()
            newDinnerMenu.menuType = menuType
            newDinnerMenu.menuList = menuList
            insert(newDinnerMenu)
            Log.i("SuggestMenuViewModel", "New Menu Creating")
        }
    }

    private suspend fun insert(newDinnerMenu: DinnerMenu){
        withContext(Dispatchers.IO){
            database.insert(newDinnerMenu)
            Log.i("SuggestMenuViewModel", "New Menu Created")
        }
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun resetNewMenuItemList(){
        _newMenuItems.value = mutableListOf()
        _newMenuLength.value = 0
    }

    fun addItemToNewMenuItems(newMenuItem: String){
        if(newMenuItem != ""){
            _newMenuItems.value?.add(newMenuItem)
            _newMenuItems.notifyObserver()
            _newMenuLength.value = _newMenuItems.value?.size
        }
    }

    fun addNewMenuToDatabase(){
        if(selectedMenuType == "tea" || selectedMenuType == "normal"){
            if(newMenuLength.value!! >0){
                createNewMenu(selectedMenuType, newMenuItems.value!!)
                resetNewMenuItemList()
                clearRadioButtons.value = true
                _toastToDisplay.value = "New menu is added"
            } else{
                _snackbarToDisplay.value = "Menu is empty!\nPlease enter an item to complete the menu."
            }
        } else{
            _snackbarToDisplay.value = "Please select a menu type."


        }
    }

    // CLEARING THE DATABASE FOR DEBUG
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