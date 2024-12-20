package com.eminokumus.learnconnect.lessons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LessonsViewModel @Inject constructor(
    private val repository: LessonsRepository
) : ViewModel() {

    private val _lessonVideosList = MutableLiveData<List<String>?>()
    val lessonVideosList: LiveData<List<String>?> get() = _lessonVideosList

    private val _currentLessonVideoUrl = MutableLiveData<String>()
    val currentLessonVideoUrl: LiveData<String> get() = _currentLessonVideoUrl


    private var currentLessonIndex = 0


    fun setLessonVideosList(list: List<String>?) {
        _lessonVideosList.value = list
        _lessonVideosList.value?.let {
            setCurrentLessonVideoUrl(it[0])
        }

    }

    fun setCurrentLessonVideoUrl(videoUrl: String) {
        _currentLessonVideoUrl.value = videoUrl

    }

    fun getCurrentLessonIndex(): Int {
        return currentLessonIndex
    }

    fun setCurrentLessonIndexWith(videoUrl: String) {
        currentLessonIndex = lessonVideosList.value!!.indexOf(videoUrl)
    }

    fun saveLessonVideoPosition(lessonPositionKey: String, currentPosition: Long) {
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            repository.saveLessonPosition(lessonPositionKey, currentPosition)
        }
    }

    suspend fun getLessonVideoPosition(lessonPositionKey: String): Flow<Long?> {
        return repository.getLessonPosition(lessonPositionKey)
    }

}