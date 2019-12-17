package com.maciej.movies4you.functional.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maciej.movies4you.models.interfaces.dao.UserDetailsDao
import com.maciej.movies4you.models.db.UserDetails

@Database(entities = [UserDetails::class], version = 2)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getUserDetailsDao(): UserDetailsDao



    companion object {
        private lateinit var INSTANCE: MyDatabase
        fun init(context: Context) {
            synchronized(MyDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java, "myDatabase.db"
                ).allowMainThreadQueries()
                    .build()
            }
        }

        val userDetailsDao get() = INSTANCE.getUserDetailsDao()
    }
}