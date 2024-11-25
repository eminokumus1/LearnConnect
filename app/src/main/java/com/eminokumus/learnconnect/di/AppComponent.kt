package com.eminokumus.learnconnect.di

import com.eminokumus.learnconnect.coursedetail.CourseDetailFragment
import com.eminokumus.learnconnect.home.HomeFragment
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.mycourses.MyCoursesFragment
import com.eminokumus.learnconnect.profile.ProfileFragment
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: MyCoursesFragment)
    fun inject(fragment: CourseDetailFragment)
}