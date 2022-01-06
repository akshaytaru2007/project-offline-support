package com.example.shadiofflinedataapp.data.remote.api

import com.example.shadiofflinedataapp.data.remote.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RandomUserApi {
    companion object {
        const val BASE_URL = "https://randomuser.me/api/"
    }

    @GET(BASE_URL)
    suspend fun getRandomUsers(@Query("results") results: Int): UserResponse

}