package com.eminokumus.learnconnect.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel(): ViewModel() {

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    private val _isPasswordConfirm = MutableLiveData<Boolean>()
    val isPasswordConfirm: LiveData<Boolean> get() = _isPasswordConfirm

    fun checkEmailFormat(email: String){
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    fun checkPasswordFormat(userPassword: String){
        _isPasswordValid.value = userPassword.length >= 6 && userPassword.isNotEmpty()
    }

    fun checkConfirmPassword(confirmPassword: String, userPassword: String){
        _isPasswordConfirm.value = userPassword == confirmPassword && confirmPassword.isNotEmpty()
    }

}