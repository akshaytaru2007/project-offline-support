package com.example.shadiofflinedataapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matched_accepted_rejected_user")
data class MatchAcceptedRejectUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userUuid: String,
    val decision: Int
) {
    constructor(
        userUuid: String,
        decision: Int
    ) : this(0, userUuid, decision)
}
