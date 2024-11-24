package com.eminokumus.learnconnect.valueobject

data class User(
    val userId: String = "",
    val email: String = "",
    val myCourses: MutableList<Course> = mutableListOf(),
    val favoriteCourses: MutableList<Course> = mutableListOf()
)
