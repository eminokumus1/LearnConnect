package com.eminokumus.learnconnect

import android.app.Application

enum class ThemeModes(){
    LIGHT, DARK
}

class MyApplication: Application() {
    var currentThemeMode = ThemeModes.LIGHT
}