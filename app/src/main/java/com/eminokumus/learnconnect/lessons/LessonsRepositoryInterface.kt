package com.eminokumus.learnconnect.lessons

import kotlinx.coroutines.flow.Flow

interface LessonsRepositoryInterface {
    suspend fun saveLessonPosition(lessonPositionKey: String, currentPosition: Long)
    suspend fun getLessonPosition(lessonPositionKey: String): Flow<Long?>
}
