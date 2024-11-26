package com.eminokumus.learnconnect.courses

import androidx.lifecycle.MutableLiveData
import com.eminokumus.learnconnect.valueobject.Course
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class CoursesRepository @Inject constructor() {

    private val coursesListMutableLiveData = MutableLiveData<MutableList<Course>>()

    fun fetchAllCoursesFromFirebase(): MutableLiveData<MutableList<Course>> {
        val database = FirebaseDatabase.getInstance()
        val coursesRef = database.getReference("courses")

        coursesRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val coursesList = mutableListOf<Course>()
                for (courseSnapshot in snapshot.children) {
                    val course = courseSnapshot.getValue(Course::class.java)
                    if (course != null) {
                        coursesList.add(course)
                    }
                }
                coursesListMutableLiveData.postValue(coursesList)
            } else {
                coursesListMutableLiveData.postValue(mutableListOf())
            }
        }.addOnFailureListener {
            coursesListMutableLiveData.postValue(mutableListOf())
        }
        return coursesListMutableLiveData
    }
}