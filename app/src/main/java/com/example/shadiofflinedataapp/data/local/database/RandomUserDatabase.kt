package com.example.shadiofflinedataapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shadiofflinedataapp.data.local.dao.MatchedAcceptRejectUserDao
import com.example.shadiofflinedataapp.data.local.dao.RandomUserDao
import com.example.shadiofflinedataapp.data.local.model.MatchAcceptedRejectUser
import com.example.shadiofflinedataapp.data.local.model.User

@Database(entities = [User::class, MatchAcceptedRejectUser::class], version = 1)
abstract class RandomUserDatabase : RoomDatabase() {
    abstract fun randomUserDao(): RandomUserDao
    abstract fun matchAcceptRejectDao(): MatchedAcceptRejectUserDao
}