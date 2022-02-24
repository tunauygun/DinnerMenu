package com.example.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "dinner_menu_table")
data class DinnerMenu(
    @PrimaryKey(autoGenerate = true)
    var menuId: Long = 0L,

    @ColumnInfo(name = "menu_type")
    var menuType: String = "random",

    @ColumnInfo(name = "menu_list")
    var menuList: MutableList<String> = mutableListOf<String>()
)

class MenuTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType = object: TypeToken<MutableList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<String?>): String {
        return  Gson().toJson(list)
    }

}