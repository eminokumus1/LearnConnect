package com.eminokumus.learnconnect.coursedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import com.eminokumus.learnconnect.valueobject.Course
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject

class CourseDetailViewModel@Inject constructor(): ViewModel() {

    private val _currentUserData = MutableLiveData<User?>()
    val currentUserData: LiveData<User?> get() = _currentUserData

    private val _course = MutableLiveData<Course>()
    val course: LiveData<Course> get() = _course

    private val _isInFavorites = MutableLiveData<Boolean>()
    val isInFavorites: LiveData<Boolean> get() = _isInFavorites

    private val _isInMyCourses = MutableLiveData<Boolean>()
    val isInMyCourses: LiveData<Boolean> get() = _isInMyCourses

    init {
        checkIfInMyCourses()
        checkIfInMyFavorites()
    }

    fun setCurrentUserData(user: User?){
        _currentUserData.value = user
    }

    fun setCourse(course: Course){
        _course.value = course
    }

    private fun checkIfInMyCourses(){
        _isInMyCourses.value = currentUserData.value?.myCourses?.contains(course.value) ?: false
    }

    private fun checkIfInMyFavorites(){
        _isInFavorites.value = currentUserData.value?.favoriteCourses?.contains(course.value) ?: false
    }

    fun setIsInMyCourses(value: Boolean){
        _isInMyCourses.value = value
    }

    fun setIsInFavorites(value: Boolean){
        _isInFavorites.value = value
    }

    fun addCourseToMyCourses(){
        _course.value?.let {
            _currentUserData.value?.myCourses?.add(it)
        }
    }
    fun removeCourseFromMyCourses(){
        _course.value?.let {
            _currentUserData.value?.myCourses?.remove(it)
        }
    }



//
//
//    fun checkIfInMyCourses(courseId:String){
//        if(userData.myCourses.include(courseId)){
//            isInMyCourse.value = true
//        }else{
//            isInMyCourse.value = false
//        }
//    }
}