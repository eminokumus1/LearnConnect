package com.eminokumus.learnconnect.profile

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import javax.inject.Inject

class ProfileRepository @Inject constructor() {

    private val auth = Firebase.auth

    fun signOut() {
        auth.signOut()
    }
}