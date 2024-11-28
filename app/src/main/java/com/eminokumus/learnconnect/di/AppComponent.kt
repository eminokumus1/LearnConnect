package com.eminokumus.learnconnect.di

import android.content.Context
import com.eminokumus.learnconnect.coursedetail.CourseDetailFragment
import com.eminokumus.learnconnect.courses.CoursesFragment
import com.eminokumus.learnconnect.lessons.LessonsFragment
import com.eminokumus.learnconnect.login.LoginActivity
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LessonsRepositoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder
        fun build(): AppComponent
    }


    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
    fun inject(fragment: CoursesFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: CourseDetailFragment)
    fun inject(fragment: LessonsFragment)
}