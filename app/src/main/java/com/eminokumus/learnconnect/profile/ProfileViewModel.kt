package com.eminokumus.learnconnect.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject

class ProfileViewModel@Inject constructor(): ViewModel() {

    private val _currentUserData = MutableLiveData<User?>()
    val currentUserData: LiveData<User?> get() = _currentUserData



    fun setCurrentUserData(user: User?){
        _currentUserData.postValue(user)
    }
}