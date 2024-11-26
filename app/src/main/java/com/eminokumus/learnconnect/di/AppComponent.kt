package com.eminokumus.learnconnect.di

import com.eminokumus.learnconnect.coursedetail.CourseDetailFragment
import com.eminokumus.learnconnect.courses.CoursesFragment
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.profile.ProfileFragment
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: CoursesFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: CourseDetailFragment)
}