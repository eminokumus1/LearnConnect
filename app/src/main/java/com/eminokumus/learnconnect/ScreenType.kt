package com.eminokumus.learnconnect

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ScreenType : Parcelable {
    HOME, MY_COURSES, FAVORITES
}