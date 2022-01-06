package com.example.shadiofflinedataapp.data.repository.user

import android.util.Log
import com.example.shadiofflinedataapp.data.local.dao.MatchedAcceptRejectUserDao
import com.example.shadiofflinedataapp.data.local.dao.RandomUserDao
import com.example.shadiofflinedataapp.data.local.model.MatchAcceptedRejectUser
import com.example.shadiofflinedataapp.data.local.model.User
import com.example.shadiofflinedataapp.data.model.UserDecision
import com.example.shadiofflinedataapp.data.model.value
import com.example.shadiofflinedataapp.data.remote.api.RandomUserApi
import com.example.shadiofflinedataapp.data.repository.mapper.UserMapper
import com.example.shadiofflinedataapp.util.Resource
import com.example.shadiofflinedataapp.util.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: RandomUserDao,
    private val matchedAcceptRejectUserDao: MatchedAcceptRejectUserDao,
    private val userApi: RandomUserApi
) : UserRepository {
    private val TAG = "UserRepositoryImpl"
    private val uniqueUserIds = mutableListOf<String>()

    override fun getMatchedUserProfiles(): Flow<Resource<List<User>>> = networkBoundResource(
        query = {
            userDao.getAllUsers()
        },
        fetch = {
            userApi.getRandomUsers(10).results
        },
        saveFetchResult = { users ->
            //Get all unique Ids
            uniqueUserIds.clear()
            uniqueUserIds.addAll(userDao.getAllUserIds())

            val parsedUsers = UserMapper.map(users)
            Log.d(TAG, "getMatchedUserProfiles: AK: ${parsedUsers.size}")
            val newUsers: List<User> = parsedUsers.filter { !uniqueUserIds.contains(it.uuid) }
            userDao.insertUsers(newUsers)

        },
        shouldFetch = { cachedList ->
            //TODO: fetched based on Timestamp or Not required as It is overkill
            true
        }
    )

    override fun getAcceptedRejectedUser(): Flow<List<MatchAcceptedRejectUser>> =
        matchedAcceptRejectUserDao.getAcceptedRejectedUser()

    override suspend fun setUserDecision(userUuid: String, decision: UserDecision) = withContext(Dispatchers.IO) {
        matchedAcceptRejectUserDao.insertMatchedUserDecision(MatchAcceptedRejectUser(userUuid = userUuid, decision = decision.value()))
    }

}