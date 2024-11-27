package com.eminokumus.learnconnect.lessons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LessonsViewModel @Inject constructor(): ViewModel() {

    private val _lessonVideosList = MutableLiveData<List<String>?>()
    val lessonVideosList: LiveData<List<String>?> get() = _lessonVideosList

    private val _currentLessonVideoUrl = MutableLiveData<String>()
    val currentLessonVideoUrl: LiveData<String> get() = _currentLessonVideoUrl

    fun setLessonVideosList(list: List<String>?){
        _lessonVideosList.value = list
        _lessonVideosList.value?.let {
            setCurrentLessonVideoUrl(it[0])
        }
        println(currentLessonVideoUrl.value)
    }

    fun setCurrentLessonVideoUrl(videoUrl: String){
        _currentLessonVideoUrl.value = videoUrl
    }
}