package com.eminokumus.learnconnect

import android.app.Application
import com.eminokumus.learnconnect.di.AppComponent
import com.eminokumus.learnconnect.di.DaggerAppComponent
import com.eminokumus.learnconnect.valueobject.User

enum class ThemeModes() {
    LIGHT, DARK
}

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().applicationContext(applicationContext).build()
    }


    var currentThemeMode = ThemeModes.LIGHT

    var currentUser: User? = null

}