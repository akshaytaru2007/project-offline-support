package com.example.shadiofflinedataapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shadiofflinedataapp.data.local.model.MatchAcceptedRejectUser
import com.example.shadiofflinedataapp.data.local.model.User
import com.example.shadiofflinedataapp.data.model.UserDecision
import com.example.shadiofflinedataapp.data.remote.api.RandomUserApi
import com.example.shadiofflinedataapp.data.repository.user.UserRepository
import com.example.shadiofflinedataapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val randomUserApi: RandomUserApi,
    private val userRepository: UserRepository

) : ViewModel() {
    private val TAG = "MainViewModel"
    private val _list: MutableLiveData<List<User>> =
        MutableLiveData(mutableListOf())
    val matchedUserList: LiveData<List<User>> = _list
    private val _acceptedRejectedUsers: MutableLiveData<List<MatchAcceptedRejectUser>> =
        MutableLiveData(mutableListOf())
    val acceptedRejectedUsers: LiveData<List<MatchAcceptedRejectUser>> = _acceptedRejectedUsers
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error: MutableLiveData<String> = MutableLiveData("")
    val error: LiveData<String> = _error

    init {
        subscribe()
    }

    private fun subscribe() {
        viewModelScope.launch {
            val matchedRejectUserFlow = userRepository.getAcceptedRejectedUser()
            val matchedUsers = userRepository.getMatchedUserProfiles()
            combine(matchedRejectUserFlow, matchedUsers) { acceptedRejectedUser, matchedUser ->
                Pair(acceptedRejectedUser, matchedUser)
            }.collect {
                val acceptedRejectUserList = it.first
                val matchedUserResponse = it.second

                _acceptedRejectedUsers.postValue(acceptedRejectUserList)
                matchedUserResponse.data.let { list -> _list.postValue(list) }
                Log.d(
                    TAG,
                    "subscribe: AK: Posting visibility Boolean: ${(matchedUserResponse is Resource.Loading)}"
                )
                _isLoading.postValue(matchedUserResponse is Resource.Loading)

                if (matchedUserResponse is Resource.Error) {
                    matchedUserResponse.error?.let { msg ->
                        _error.postValue(msg)
                    }
                } else {
                    _error.postValue("")
                }
            }
        }
    }

    fun setAcceptRejectUser(uuid: String, decision: UserDecision) {
        viewModelScope.launch {
            userRepository.setUserDecision(uuid, decision)
        }
    }

}