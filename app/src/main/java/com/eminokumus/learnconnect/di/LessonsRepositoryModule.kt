package com.eminokumus.learnconnect.di

import android.content.Context
import com.eminokumus.learnconnect.lessons.LessonsRepository
import com.eminokumus.learnconnect.lessons.LessonsRepositoryInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LessonsRepositoryModule {

    @Singleton
    @Provides
    fun provideLessonsRepository(context: Context): LessonsRepositoryInterface{
        return LessonsRepository(context)
    }

}