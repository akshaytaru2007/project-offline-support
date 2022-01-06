package com.example.shadiofflinedataapp.data.repository.mapper

import com.example.shadiofflinedataapp.data.local.model.Address
import com.example.shadiofflinedataapp.data.local.model.Name
import com.example.shadiofflinedataapp.data.local.model.User as LocalUser
import com.example.shadiofflinedataapp.data.remote.model.User as RemoteUser

object UserMapper {
    fun map(remoteUser: RemoteUser): LocalUser {
        return LocalUser(
            uuid = remoteUser.login.uuid,
            name = Name(
                title = remoteUser.name.title,
                firstName = remoteUser.name.first,
                lastName = remoteUser.name.last
            ),
            email = remoteUser.email,
            picture = remoteUser.picture.large,
            gender = remoteUser.gender,
            age = remoteUser.dob.age,
            address = Address(
                city = remoteUser.location.city,
                state = remoteUser.location.state,
                country = remoteUser.location.country
            )
        )
    }

    fun map(remoteUsers: List<RemoteUser>): List<LocalUser> {
        val list = mutableListOf<LocalUser>()
        remoteUsers.forEach {
            list.add(map(it))
        }
        return list;
    }
}