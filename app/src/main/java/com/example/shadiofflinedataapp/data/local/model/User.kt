package com.example.shadiofflinedataapp.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val uuid: String,
    @Embedded
    val name: Name,
    val email: String,
    val picture: String,
    val gender: String,
    val age: Int,
    @Embedded
    val address: Address
)

data class Name(
    val title: String,
    val firstName: String,
    val lastName: String,
)

data class Address(
    val city: String,
    val state: String,
    val country: String
)
