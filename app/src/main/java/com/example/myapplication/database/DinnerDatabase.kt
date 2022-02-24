package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DinnerMenu::class], version = 1, exportSchema = false)
@TypeConverters(MenuTypeConverter::class)
abstract class DinnerDatabase : RoomDatabase(){

    abstract val dinnerMenuDao : DinnerMenuDao

    companion object{

        @Volatile
        private var INSTANCE: DinnerDatabase? = null

        fun getInstance(context: Context) : DinnerDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, DinnerDatabase::class.java, "dinner_menu_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}