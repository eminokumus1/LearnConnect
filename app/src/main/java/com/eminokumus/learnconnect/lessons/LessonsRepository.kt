package com.eminokumus.learnconnect.lessons

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("user_preferences")

class LessonsRepository@Inject constructor(private val context: Context): LessonsRepositoryInterface {
    override suspend fun saveLessonPosition(lessonPositionKey: String, currentPosition: Long) {
        val LESSON_POSITION_KEY = longPreferencesKey(lessonPositionKey)
        context.dataStore.edit { preferences->
            preferences[LESSON_POSITION_KEY] = currentPosition
        }
    }

    override suspend fun getLessonPosition(lessonPositionKey: String): Flow<Long?> {
        val LESSON_POSITION_KEY = longPreferencesKey(lessonPositionKey)
        return context.dataStore.data.map { preferences->
            preferences[LESSON_POSITION_KEY] ?: 0
        }
    }
}