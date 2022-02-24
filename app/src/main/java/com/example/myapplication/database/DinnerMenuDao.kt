package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DinnerMenuDao {

    @Insert
    fun insert(menu: DinnerMenu)

    @Update
    fun update(menu: DinnerMenu)

    @Query("SELECT * FROM dinner_menu_table WHERE menu_type = :required_menu_type ORDER BY RANDOM() LIMIT 1")
    fun getRandomMenu(required_menu_type: String): DinnerMenu

    @Query("SELECT * FROM dinner_menu_table ORDER BY menuId DESC")
    fun getAllMenus(): LiveData<List<DinnerMenu>>

    @Query("DELETE FROM dinner_menu_table")
    fun clear()

}