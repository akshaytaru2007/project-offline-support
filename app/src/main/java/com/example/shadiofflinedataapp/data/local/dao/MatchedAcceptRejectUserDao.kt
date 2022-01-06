package com.example.shadiofflinedataapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shadiofflinedataapp.data.local.model.MatchAcceptedRejectUser
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchedAcceptRejectUserDao {

    @Query("Select * from matched_accepted_rejected_user")
    fun getAcceptedRejectedUser(): Flow<List<MatchAcceptedRejectUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatchedUserDecision(users: MatchAcceptedRejectUser)


}