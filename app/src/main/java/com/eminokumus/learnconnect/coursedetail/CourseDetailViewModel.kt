package com.eminokumus.learnconnect.coursedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.learnconnect.valueobject.Course
import com.eminokumus.learnconnect.valueobject.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class CourseDetailViewModel@Inject constructor(): ViewModel() {

    private val auth = Firebase.auth
    private val dbFirebase = FirebaseDatabase.getInstance()
    private val currentUserUid = auth.currentUser?.uid
    private val userRef = currentUserUid?.let { dbFirebase.getReference("users").child(it) }



    private val _currentUserData = MutableLiveData<User?>()
    val currentUserData: LiveData<User?> get() = _currentUserData

    private val _course = MutableLiveData<Course>()
    val course: LiveData<Course> get() = _course

    private val _isInFavorites = MutableLiveData<Boolean>()
    val isInFavorites: LiveData<Boolean> get() = _isInFavorites

    private val _isInMyCourses = MutableLiveData<Boolean>()
    val isInMyCourses: LiveData<Boolean> get() = _isInMyCourses



    fun setCurrentUserData(user: User?){
        _currentUserData.value = user
    }

    fun setCourse(course: Course){
        _course.value = course
    }

     fun checkIfInMyCourses(){
        println("currentUserData: ${currentUserData.value}")
        _isInMyCourses.value = currentUserData.value?.myCourses?.contains(course.value) ?: false
    }

     fun checkIfInMyFavorites(){
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
            updateFirebaseUser()
        }
    }
    fun removeCourseFromMyCourses(){
        _course.value?.let {
            _currentUserData.value?.myCourses?.remove(it)
            updateFirebaseUser()
        }
    }

    fun addCourseToFavorites(){
        _course.value?.let {
            _currentUserData.value?.favoriteCourses?.add(it)
            updateFirebaseUser()
        }
    }
    fun removeCourseToFavorites(){
        _course.value?.let {
            _currentUserData.value?.favoriteCourses?.remove(it)
            updateFirebaseUser()
        }
    }

    private fun updateFirebaseUser(){
        userRef?.child("userData")?.setValue(currentUserData.value)
    }
}