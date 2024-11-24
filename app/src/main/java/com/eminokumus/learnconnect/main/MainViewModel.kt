package com.eminokumus.learnconnect.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject

class MainViewModel@Inject constructor(private val repository: MainRepository): ViewModel() {

    val currentUser: LiveData<User?> = repository.fetchUserFromDatabase()
}