package com.example.shadiofflinedataapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shadiofflinedataapp.data.local.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomUserDao {

    @Query("Select * from users")
    fun getAllUsers(): Flow<List<User>>

    @Query("Select uuid from users")
    suspend fun getAllUserIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)
}