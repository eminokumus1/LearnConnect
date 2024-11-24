package com.eminokumus.learnconnect

import android.app.Application
import com.eminokumus.learnconnect.di.AppComponent
import com.eminokumus.learnconnect.di.DaggerAppComponent
import com.eminokumus.learnconnect.valueobject.User
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

enum class ThemeModes(){
    LIGHT, DARK
}

class MyApplication: Application() {

    val appComponent: AppComponent by lazy{
        DaggerAppComponent.create()
    }

    var currentThemeMode = ThemeModes.LIGHT


}