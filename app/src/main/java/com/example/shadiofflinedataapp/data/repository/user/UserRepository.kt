package com.example.shadiofflinedataapp.data.repository.user

import com.example.shadiofflinedataapp.data.local.model.MatchAcceptedRejectUser
import com.example.shadiofflinedataapp.data.local.model.User
import com.example.shadiofflinedataapp.data.model.UserDecision
import com.example.shadiofflinedataapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMatchedUserProfiles(): Flow<Resource<List<User>>>

    fun getAcceptedRejectedUser(): Flow<List<MatchAcceptedRejectUser>>

    suspend fun setUserDecision(userUuid: String, decision: UserDecision)
}