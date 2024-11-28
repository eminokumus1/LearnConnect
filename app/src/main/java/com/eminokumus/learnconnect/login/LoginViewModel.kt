package com.eminokumus.learnconnect.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    fun checkEmailFormat(email: String){
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    fun checkPasswordFormat(userPassword: String){
        _isPasswordValid.value = userPassword.length >= 6 && userPassword.isNotEmpty()
    }



}