package com.example.shadiofflinedataapp.data.model

sealed class UserDecision() {
    class Accepted: UserDecision()
    class Reject: UserDecision()
    class Pending: UserDecision()
}

fun UserDecision.value(): Int {
    return when (this) {
        is UserDecision.Accepted -> 1
        is UserDecision.Reject -> 2
        is UserDecision.Pending -> 0
    }
}


