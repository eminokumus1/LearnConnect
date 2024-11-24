package com.eminokumus.learnconnect.main

import androidx.lifecycle.MutableLiveData
import com.eminokumus.learnconnect.valueobject.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class MainRepository @Inject constructor() {

    private val auth = Firebase.auth
    private val currentUserUid = auth.currentUser?.uid

    private val currentUserLiveData = MutableLiveData<User?>()

    fun fetchUserFromDatabase(): MutableLiveData<User?> {
        val database = FirebaseDatabase.getInstance()
        currentUserUid?.let {
            val userRef = database.getReference("users").child(currentUserUid)

            userRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    for (item in snapshot.children) {
                        currentUserLiveData.postValue(item.getValue(User::class.java))
                    }
                } else {
                    currentUserLiveData.postValue(null)
                }

            }.addOnFailureListener {
                currentUserLiveData.postValue(null)
            }
        }
        return currentUserLiveData
    }

}