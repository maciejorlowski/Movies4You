package com.maciej.movies4you.models.interfaces.dao

import androidx.room.*
import com.maciej.movies4you.models.db.UserDetails

@Dao
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetails: UserDetails)

    @Query("DELETE FROM userDetails")
    fun delete()

    @Query("SELECT username FROM userDetails")
    fun getUsername() : String
}